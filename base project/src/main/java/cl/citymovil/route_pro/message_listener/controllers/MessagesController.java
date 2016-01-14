package cl.citymovil.route_pro.message_listener.controllers;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.DeferredResult;

import cl.citymovil.route_pro.message_listener.domain.GpsMessage;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.ScheduledCustomer;
import cl.citymovil.route_pro.message_listener.services.DatabaseService;
import cl.citymovil.route_pro.message_listener.services.GeoService;

@Controller
public class MessagesController {
	
	@Autowired
	GeoService geoService;
	
	@Autowired
	DatabaseService databaseService;
	
	Logger logger = LoggerFactory.getLogger(MessagesController.class);

	@RequestMapping(value = "/testinsert", method = RequestMethod.GET)//, consumes = "application/json",produces = "application/json"
//	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody String testinsert() {
	
		Location location = new Location();
		location.setLatitude(-33);
		location.setLongitude(-71);
		
		databaseService.addVehiclePositionTest(location);
		
		return "ey!";
	}

	
	
	@RequestMapping(value = "/receive", method = RequestMethod.POST)//, consumes = "application/json",produces = "application/json"
//	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody String test(@RequestBody String originalMessage ) {

		logger.info("mensage recibido: "+originalMessage);
		
		String message = "";
		
		try {
			message = java.net.URLDecoder.decode(originalMessage, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			logger.error("no se puede decodificar: "+originalMessage);
			e1.printStackTrace();
		}
		
		//por alguna razón el mensaje de CO2 viene con un signo "=" al final
		message = message.replace("=","");
		
		GpsMessage gpsMessage = null;
		String returnString = "null";
		try {
			
			JAXBContext jaxbContext = JAXBContext.newInstance(GpsMessage.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(message);
			gpsMessage = (GpsMessage) jaxbUnmarshaller.unmarshal(reader);
			returnString = ToStringBuilder.reflectionToString(gpsMessage, ToStringStyle.MULTI_LINE_STYLE);
			
//			Vehicle vehicle = databaseService.getVehicleByExternalId(gpsMessage.getExternalId());
			
			//mensage de posición por tiempo, se actualiza la tabla last_position
			if(gpsMessage.getType()==14)
			{
				logger.debug("El móvil "+gpsMessage.getExternalId()+" ha enviado un mensaje de posición.");
				updateVehicle(gpsMessage);
				checkScheduledCustomers(gpsMessage);
			}
			
			//mensage de detención , se busca si es una detención cerca (a menos de 100 metros) de clientes de la ruta.
			else if(gpsMessage.getType()==0)
			{
				logger.debug("El móvil "+gpsMessage.getExternalId()+" ha enviado un mensaje de detención ");
				checkScheduledCustomers(gpsMessage);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//			logger.error("error en jaxbUnmarshaller!!!");
		}
				
		
		return returnString;
				
		
	}
	
	public void checkScheduledCustomers(GpsMessage gpsMessage)
	{
		List<ScheduledCustomer> scheduledCustomers = databaseService.getScheduledCustomersByVehicleExternalId(gpsMessage.getExternalId());
		
//		logger.debug("scheduledCustomer size : "+ scheduledCustomers.size());
		
		long result = geoService.isNearScheduledCustomer(scheduledCustomers, new Location(gpsMessage.getLatitude(), gpsMessage.getLongitude()));
		
		if(result>0)
		{
			databaseService.updateScheduledCustomerRealArrivalTime(result, gpsMessage.getDatetime());
			logger.debug("El móvil "+gpsMessage.getExternalId()+" está a menos de 100 metros del cliente programado "+result+" ");
		}
	}
	
	public void updateVehicle(GpsMessage gpsMessage)
	{
		databaseService.addVehiclePosition(gpsMessage.getExternalId(), gpsMessage.getLatitude(), gpsMessage.getLongitude(), gpsMessage.getDatetime());
		databaseService.updateVehicleLastPosition(gpsMessage.getExternalId(), gpsMessage.getLatitude(), gpsMessage.getLongitude(), gpsMessage.getDatetime());
	}
	

}
