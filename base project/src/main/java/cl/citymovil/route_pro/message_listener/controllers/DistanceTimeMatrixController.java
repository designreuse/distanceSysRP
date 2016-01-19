package cl.citymovil.route_pro.message_listener.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cl.citymovil.route_pro.message_listener.services.DistanceMatrixService;
import cl.citymovil.route_pro.message_listener.services.DistanceMatrixServiceImpl;
import cl.citymovil.route_pro.solver.util.LocationConteiner;

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
    public String onSubmit()
    {
    	System.out.println("PAsando por el onSubmit via GET");
    	
    	//DistanceMatrixService distanceMatrixService = new DistanceMatrixServiceImpl();
    	
    
    	LocationConteiner locationConteiner = distanceMatrixService.Preprocess();
    	
   	 System.out.println (":::::: DESDE DISTANCETIME CONTROLLER ::::::::::");
   	 locationConteiner.listLocation();
   	locationConteiner.listTmpLocation();
    	
    	distanceMatrixService.Process(locationConteiner.getLocationTmp(), locationConteiner.getLocation());
    	
   
    	//DistanceMatrixServiceImp-Preprocess
    	
		/*
        double lat = locationValidator.getPercentage();
        double lon = 
        logger.info("Increasing prices by " + increase + "%.");
    

        locationManager.getValidateLocation(, lon);    */
    	System.out.println(" >>>>>>>>>>> Saliendo y Mandando una pagina que no existe<<<<<<<<<<<");

        return "redirect:/hello.htm";
    }

    //@RequestMapping(method = RequestMethod.GET)
    protected void formBackingObject(HttpServletRequest request) throws ServletException {
        System.out.println("PAsando por el formBackObject UUUUUUhhhhhhh");
    }

    public void setLocationManager(DistanceMatrixService distanceMatrixService) {
        this.distanceMatrixService = distanceMatrixService;
    }

    public DistanceMatrixService getProductManager() {
        return distanceMatrixService;
    }

	

}
