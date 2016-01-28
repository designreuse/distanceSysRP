package cl.citymovil.route_pro.message_listener.controllers;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cl.citymovil.route_pro.message_listener.services.DistanceMatrixService;
import cl.citymovil.route_pro.solver.util.LocationContainer;
import cl.citymovil.route_pro.solver.util.RelationLocation;

@Controller
@RequestMapping(value="/requestalpha.htm")
public class DistanceTimeMatrixAlphaController {
	
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private DistanceMatrixService distanceMatrixService;
    
    @RequestMapping(method = RequestMethod.GET)
     public void onSubmit()
     {
    	System.out.println("Holanda, que talca");
//     	LocationContainer locationConteiner = distanceMatrixService.Preprocess();
//     	if(locationConteiner==null){
//     		System.out.println("No hay LocationTmp para procesar.");
//     		
//     	}else{
// 		   	 locationConteiner.listLocation();
// 		   	 locationConteiner.listTmpLocation();
// 		   	ArrayList<RelationLocation>  distanceMatrixList = distanceMatrixService.Process(locationConteiner);
// 		   	
// 		   	distanceMatrixService.PostProcess(distanceMatrixList);
//    	
//     	} 
     }
    protected void formBackingObject(HttpServletRequest request) throws ServletException {
    }

    public void setLocationManager(DistanceMatrixService distanceMatrixService) {
        this.distanceMatrixService = distanceMatrixService;
    }

    public DistanceMatrixService getProductManager() {
        return distanceMatrixService;
    } 

}
