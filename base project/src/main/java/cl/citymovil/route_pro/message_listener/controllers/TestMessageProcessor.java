package cl.citymovil.route_pro.message_listener.controllers;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import cl.citymovil.route_pro.message_listener.domain.GpsMessage;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.ScheduledCustomer;
import cl.citymovil.route_pro.message_listener.services.DatabaseService;
import cl.citymovil.route_pro.message_listener.services.GeoService;

@Component
@Scope("prototype")
public class TestMessageProcessor {

	@Autowired
	GeoService geoService;
	
	@Autowired
	DatabaseService databaseService;
	
	Logger logger = LoggerFactory.getLogger(MessagesController.class);
	
	public TestMessageProcessor(){};
	
	public TestMessageProcessor(String message)
	{
		this.test(message);
	}
	
	public String test(String message ) {

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
			if(gpsMessage.getType()==8)
			{
				logger.debug("El móvil "+gpsMessage.getExternalId()+" ha enviado un mensaje de posición.");
				databaseService.addVehiclePosition(gpsMessage.getExternalId(), gpsMessage.getLatitude(), gpsMessage.getLongitude(), gpsMessage.getDatetime());
				databaseService.updateVehicleLastPosition(gpsMessage.getExternalId(), gpsMessage.getLatitude(), gpsMessage.getLongitude(), gpsMessage.getDatetime());
			}
			
			//mensage de detención , se busca si es una detención cerca (a menos de 100 metros) de clientes de la ruta.
			else if(gpsMessage.getType()==1)
			{
				logger.debug("El móvil "+gpsMessage.getExternalId()+" ha enviado un mensaje de detención ");
				List<ScheduledCustomer> scheduledCustomers = databaseService.getScheduledCustomersByVehicleExternalId(gpsMessage.getExternalId());
				
				logger.debug("scheduledCustomer size : "+ scheduledCustomers.size());
				
				long result = geoService.isNearScheduledCustomer(scheduledCustomers, new Location(gpsMessage.getLatitude(), gpsMessage.getLongitude()));
				
				if(result>0)
				{
					databaseService.updateScheduledCustomerRealArrivalTime(result, gpsMessage.getDatetime());
					logger.debug("El móvil "+gpsMessage.getExternalId()+" está a menos de 100 metros del cliente programado "+result+" ");
				} 

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("error en jaxbUnmarshaller!!!");
		}
		
		return returnString;
	}
}
