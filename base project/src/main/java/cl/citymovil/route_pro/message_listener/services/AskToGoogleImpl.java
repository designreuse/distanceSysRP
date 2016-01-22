package cl.citymovil.route_pro.message_listener.services;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;

import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.LocationTmp;
import cl.citymovil.route_pro.solver.util.DistanceTimeMatrixUtility;
import cl.citymovil.route_pro.solver.util.LocationContainer;



@Service
public class AskToGoogleImpl implements AskToGoogle{
	
	 protected final Log logger = LogFactory.getLog(getClass());
	
	 protected final int MaxConsultGoogle = 25;

@Autowired
DistanceTimeMatrixUtility[] matrizArrayOrigDestTimeDur;


	

	@Override
	public  DistanceTimeMatrixUtility[] getDistanceByGoogle(LocationContainer locationContainer){//String[] newLocations,String[] oldLocations) {
		  //GeoApiContext context = new GeoApiContext().setApiKey("j61PmiK8glmpGIVYL47RQrm_zbKk=");//"AIzaSyB-ZZHRgGvMLczqzDZnmFBds4Zs27wm1AY");//AIzaSyBc-yEd3hfr4Q9GEVf2uYu_JaGbtLNlt7Y
		   GeoApiContext context = new GeoApiContext().setEnterpriseCredentials("gme-bigservicespacityplanning", "j-NB2bOlmRUMInQ459WVwrf7O9w=");
		  
		  //1- necesito ingresar las nueva locaciones a la tabla de location para obtener su id
		  
		  //2-ordenar todos las locaciones en un nuevo arreglo
		  
		  //3- dividir ese arreglo en secciones de 25 string[]
		  
		  //4-Consultar al Google
		  
		  //5-Retornar un array con todos los nuevos puntos y pasarlos al postProcess
		  
		  //-----------------------------------------------------------------------------------//
		  
		  List<Location>  oldLocations= locationContainer.getLocation();
		  List<LocationTmp> newLocations = locationContainer.getLocationTmp();
		  Integer sizeNewLocation = newLocations.size();
		  Integer sizeOldLocation = oldLocations.size();
		  int contDeConsultas=1;
		  int contDeArreglos=0;
		  LocationTmp cabeza;
		  Location cabezaOld;
		  String positionStringNew=new String();
		  List <String> newPositionStringList=new ArrayList <String>();
		  List <String> oldPositionStringList=new ArrayList <String>();
		  String[] origenPosition;
		  String[] destinyPosition;
		  List <String[]> origenLocationsList= new ArrayList <String[]>();
		  List <String[]> destinyLocationsList= new ArrayList <String[]>();
		  Boolean flag=true;
		  
		  //obtengo una nueva locacion
		  for(int contNewLoc=0; contNewLoc < sizeNewLocation; contNewLoc++){
			//obtengo una posicion nueva y despues de eso genero todas el arreglo de consultas hasta completar un array de largo 25,
			  //si el array no llega a 25, es copiado entero en el sgte paso gracias a un flag que identifica esta situacion.
		
			  positionStringNew=newLocations.get(contNewLoc).getLatitudeTmp()+","+newLocations.get(contNewLoc).getLongitudeTmp();
			
			  //genero la lista de consulta con entre la nueva locacion y las que ya estan
			for(int contOldLoc=0; contOldLoc < sizeOldLocation; contOldLoc++){
				//ORIGEN
				newPositionStringList.add(contOldLoc, positionStringNew);
				
				//DESTINO
				oldPositionStringList.add(contOldLoc,oldLocations.get(contOldLoc).getLatitude()+","+oldLocations.get(contOldLoc).getLongitude());
			    
				//creado el destino y origen para la primera consulta, se aumenta el contador de consultas, que hace de contador de largo del arreglo
				contDeConsultas++;
				
				//verifico si se ha llegado al largo de 25 	
				if(contDeConsultas==MaxConsultGoogle){//newPositionStringList.size()==25
					//transformo los List <String> a String[]
					origenPosition = newPositionStringList.toArray(new String[newPositionStringList.size()]);
					destinyPosition = oldPositionStringList.toArray(new String[oldPositionStringList.size()]);
					
					//almaceno los String[25] en un List <String[]> que luego utilizaré para consultar a google
					origenLocationsList.add(contDeArreglos, origenPosition);
					destinyLocationsList.add(contDeArreglos, destinyPosition);	
					contDeConsultas=0;
					contDeArreglos++;
					newPositionStringList.clear();
					oldPositionStringList.clear();
					flag=false;
					
				}else{
					flag=true;
				}
			}
		  }
		  if(flag==true && (newPositionStringList.size()!=0 || oldPositionStringList.size()!=0)){
			  origenPosition = newPositionStringList.toArray(new String[newPositionStringList.size()]);
			  destinyPosition = oldPositionStringList.toArray(new String[oldPositionStringList.size()]);
			  origenLocationsList.add(contDeArreglos, origenPosition);
			  destinyLocationsList.add(contDeArreglos, destinyPosition);	
			  
		  }
		  
		  
		  ////////////////////AQUI VOY //////////////////////////////////////////
		  
		  
			//String newPosition[] = {newLocations.get(contNewLoc).getLatitudeTmp()+","+newLocations.get(contNewLoc).getLongitudeTmp()};
		 
		  //FUNCIONA String[] newPosition = newPositionStringList.toArray(new String[newPositionStringList.size()]);
		   
		   //String[] strarray = strlist.toArray(new String[0]);
		   //PROCESO INVERSO, PASAR DE STRING[] A LIST <STRING>:-> Arrays.toString(strarray)
		  
		  System.out.println("Tamaño origenLocationsList.size():"+origenLocationsList.size());
		  System.out.println("Tamaño destinyLocationsList.size():"+destinyLocationsList.size());
		   //DistanceTimeMatrixUtility[] matrizArrayOrigDestTimeDur= new DistanceTimeMatrixUtility[contDeArreglos+1];
		   Integer count;
		  
			 
				 // String oldPosition[] ={ oldLocations.get(contOldLoc).getLatitude()+","+oldLocations.get(contOldLoc).getLongitude()};
			    
				 System.out.println(":::::::::PREGUNSTANDO A GOOGLE:::::::::");
				  	try {
				  		 for(count=0;  count < contDeArreglos ; count++){
				  			 
					  		System.out.println("::::Contador:"+count);
					  		 System.out.println("::::Tamaño origenLocationsList.get(contOldLoc).length:"+origenLocationsList.get(count).length);
							  System.out.println(":::Tamaño destinyLocationsList.get(contOldLoc).length:"+destinyLocationsList.get(count).length);
					  		  DistanceMatrix result = DistanceMatrixApi.getDistanceMatrix(context, origenLocationsList.get(count), destinyLocationsList.get(count) ).await();
					  	
					  		matrizArrayOrigDestTimeDur[count].setDistanceMatrix( result);
					  		matrizArrayOrigDestTimeDur[count].setOrigen(origenLocationsList.get(count));
					  		matrizArrayOrigDestTimeDur[count].setDestiny(destinyLocationsList.get(count));
				  		 }
				  		
//				  		System.out.println("::Origen::"+result.destinationAddresses[0]);
//					  	System.out.println("::Destino::"+result.destinationAddresses.toString());
//					  	System.out.println("::cantidad de Elementos::"+result.rows[0].elements.length);
					  	
				  		  
				  	
				  	} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
		   
				  
		  
		  //----------------------------------FIN-----------------------//
		  
		
		return matrizArrayOrigDestTimeDur;//arrayDistanceMatrixResult;       
	
	}

	@Override
	public void getTimeByGoogle() {
		// TODO Auto-generated method stub
//		return null;
	}

}
