package cl.citymovil.route_pro.message_listener.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.maps.model.DistanceMatrix;

import cl.citymovil.route_pro.message_listener.services.DistanceMatrixService;
import cl.citymovil.route_pro.message_listener.services.DistanceMatrixServiceImpl;
import cl.citymovil.route_pro.solver.util.DistanceTimeMatrixUtility;
import cl.citymovil.route_pro.solver.util.LocationContainer;
import cl.citymovil.route_pro.solver.util.RelationLocation;

@Controller
@RequestMapping(value="/priceincrease.htm")
public class DistanceTimeMatrixController {
	
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private DistanceMatrixService distanceMatrixService;

   // @RequestMapping(method = RequestMethod.POST)
    @RequestMapping(method = RequestMethod.GET)
   //@RequestMapping(value = "/priceincrease", method = RequestMethod.GET)
    public void onSubmit()
    {
//    	System.out.println("PAsando por el onSubmit via GET");
    	
    	//DistanceMatrixService distanceMatrixService = new DistanceMatrixServiceImpl();
    	
//    	System.out.println ("::::::INICIANDO PREPROCESS::::::::::");
    	LocationContainer locationConteiner = distanceMatrixService.Preprocess();
    	
//   	 System.out.println (":::::: DESDE DISTANCETIME CONTROLLER ::::::::::");
   	 locationConteiner.listLocation();
   	locationConteiner.listTmpLocation();
   	
   	
//   	System.out.println (":::::: INICIANDO PROCESS ::::::::::");
   	ArrayList<RelationLocation>  distanceMatrixList = distanceMatrixService.Process(locationConteiner);
   	
   	distanceMatrixService.PostProcess(distanceMatrixList);
    
    
    	
   
    	//DistanceMatrixServiceImp-Preprocess
    	
		/*
        double lat = locationValidator.getPercentage();
        double lon = 
        logger.info("Increasing prices by " + increase + "%.");
    

        locationManager.getValidateLocation(, lon);    */
//    System.out.println("::::::::::::::Mostrando el notenido de distanceMatrixList post Process:::::::::::::::");
   // System.out.println(ToStringBuilder.reflectionToString(distanceMatrixList, ToStringStyle.MULTI_LINE_STYLE));
//    	System.out.println(" >>>>>>>>>>> Saliendo y Mandando una pagina que no existe<<<<<<<<<<<");
//    	System.out.println(" >>>>>>>>>>> ::::::::: FIN ::::::::<<<<<<<<<<<");
//    	System.out.println(" >>>>>>>>>>> ::::::::: FIN ::::::::<<<<<<<<<<<");
//    	System.out.println(" >>>>>>>>>>> ::::::::: FIN ::::::::<<<<<<<<<<<");

        
    }

    //@RequestMapping(method = RequestMethod.GET)
    protected void formBackingObject(HttpServletRequest request) throws ServletException {
//        System.out.println("PAsando por el formBackObject UUUUUUhhhhhhh");
    }

    public void setLocationManager(DistanceMatrixService distanceMatrixService) {
        this.distanceMatrixService = distanceMatrixService;
    }

    public DistanceMatrixService getProductManager() {
        return distanceMatrixService;
    }

	

}
