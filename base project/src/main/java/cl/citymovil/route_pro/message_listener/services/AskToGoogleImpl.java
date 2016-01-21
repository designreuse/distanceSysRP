package cl.citymovil.route_pro.message_listener.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;

import cl.citymovil.route_pro.message_listener.domain.DistanceTimeMatriz;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.LocationTmp;
import cl.citymovil.route_pro.solver.util.DistanceTimeMatrixUtility;
import cl.citymovil.route_pro.solver.util.LocationContainer;



@Service
public class AskToGoogleImpl implements AskToGoogle{
	
	
private static int MaxConsultGoogle = 25;

@Autowired
DistanceTimeMatrixUtility distanceTimeMatrixUtility;
	
	@SuppressWarnings("null")
	@Override
	public DistanceTimeMatrixUtility getDistanceByGoogle(LocationContainer locationContainer){//String[] newLocations,String[] oldLocations) {
		  GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyB-ZZHRgGvMLczqzDZnmFBds4Zs27wm1AY");
		  
		  //1- necesito ingresar las nueva locaciones a la tabla de location para obtener su id
		  
		  //2-ordenar todos las locaciones en un nuevo arreglo
		  
		  //3- dividir ese arreglo en secciones de 25 string[]
		  
		  //4-Consultar al Google
		  
		  //5-Retornar un array con todos los nuevos puntos y pasarlos al postProcess
		  
		  //-----------------------------------------------------------------------------------//
		  
		  List<Location>  oldLocations= locationContainer.getLocation();
		  List<LocationTmp> newLocations = locationContainer.getLocationTmp();
		  
		  Integer SizeNewLocation = newLocations.size();
		  
		  Integer SizeOldLocation = oldLocations.size();
		  
		  Integer TotalLocation= SizeNewLocation + SizeOldLocation;
		  
		  int contDeConsultas=0;
		  int contDeArreglos=0;
		  LocationTmp cabeza;
		  List <LocationTmp> cola;
		  Location cabezaOld;
		  List <Location> colaOld;
		  List <String[]> originsArrayList = new ArrayList <String[]>();
		  List <String[]> destinyArrayList = new ArrayList <String[]>();
		  List <String> origins = new ArrayList<String>();
		  List <String> destiny = new ArrayList<String>();
		  
		  boolean flagRevisionArrayIncompletos=true;
		  
		  for(int i=0; i< SizeNewLocation; i++){
			  cabeza = newLocations.get(i);
			  cola= newLocations.subList(i+1, SizeNewLocation);
			  
			  
			  for(int j=0; j<SizeOldLocation; j++){
				  cabezaOld = oldLocations.get(j);
				  colaOld= oldLocations.subList(j+1, SizeOldLocation);
				  
			/*
			 * 
			 * List<String> a = new ArrayList<String>();
					a.add("kk");
					a.add("pp");
					
					And then you can have an array again by using toArray:
					
					String[] myArray = new String[a.size()];
					a.toArray(myArray);
			 * 
			 * 
			 */
				  System.out.println("::: ESTO ES LO QUE ENCUENTRO EN ORIGINS PREVIO A LA CAIDA "+cabeza.getLatitudeTmp()+","+cabeza.getLongitudeTmp()+"::::");
				  System.out.println("::: ESTO ES LO QUE ENCUENTRO EN ORIGINS PREVIO A LA CAIDA "+cabezaOld.getLatitude()+","+cabezaOld.getLongitude()+"::::");
				  System.out.println("::: CONTADORES I:"+i+":: J:"+j+"::");
				  
				  String temp =  cabezaOld.getLatitude()+","+cabezaOld.getLongitude();
				  
				  destiny.add(temp);
				  origins.add(temp);//cabeza.getLatitudeTmp().toString()+","+cabeza.getLongitudeTmp().toString()
				  
				  if(contDeConsultas+1==MaxConsultGoogle){
					  String[] OriginsStringArray = new String[origins.size()];
					  origins.toArray(OriginsStringArray);
					  originsArrayList.add(contDeArreglos, OriginsStringArray);
					  
					  String[] DestinysStringArray = new String[destiny.size()];
					  destiny.toArray(DestinysStringArray);
					  destinyArrayList.add(contDeArreglos, DestinysStringArray);
					  
					  contDeArreglos++;
					  contDeConsultas=0;
					  origins.clear();
					  destiny.clear();
					  flagRevisionArrayIncompletos=false;
				  }else{
					  flagRevisionArrayIncompletos=true;
					  contDeConsultas++;
				  }
				  
				  
			  }
			  
		  }
		  
		  if(flagRevisionArrayIncompletos==true){
			  String[] OriginsStringArray = new String[origins.size()];
			  String[] DestinysStringArray = new String[destiny.size()];
			  destiny.toArray(DestinysStringArray);
			  origins.toArray(OriginsStringArray);
			  
			  originsArrayList.add(contDeArreglos, OriginsStringArray);
			  destinyArrayList.add(contDeArreglos, OriginsStringArray);
			  contDeConsultas++;
		  }
		  for(int cont=0; cont <= contDeConsultas; cont++) {
			  System.out.println(":::::::::CONT:"+cont+"::::::::::");
			  	try {
			  		DistanceMatrix result = DistanceMatrixApi.getDistanceMatrix(context, originsArrayList.get(cont), destinyArrayList.get(cont)).await();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		  }
		  
//		  for(int i=0; i< SizeNewLocation; i++){
//			  cabeza = newLocations.get(i);
//			  cola= newLocations.subList(i+1, SizeNewLocation);
//			  for(int j=0; j < cola.size(); j++){
//				 
//				  	Origins[j]=cabeza.getLatitudeTmp()+","+cabeza.getLongitudeTmp();
//				  	Destiny[j]=cola.get(j).getLatitudeTmp()+","+cola.get(j).getLongitudeTmp();
//				  	
//				  	contDeConsultas++;
//					  if(contDeConsultas==24){
//						  OriginsArrayList.add(contDeArreglos, Origins);
//						  DestinyArrayList.add(contDeArreglos, Destiny);
//						  contDeArreglos++;
//					  }
//			  }
//			  if(i+1==SizeNewLocation){//revision previa al final del primer for
//				  
//			  }
			  
			  

//			  if(i%(MaxConsultGoogle)==0 ){
//				  Origins.set(contDeConsultas, ArrayLocation);
//				  contDeConsultas++; 
//				  
//			  }else if(i+1 <= SizeNewLocation ){//llega al termino del ciclo sin tener llegar a 25 o un multiplo de este
//				  
//				  
//			  }
			  
		  
//		  ArrayLocation[i-1]= newLocations.get(i-1).getLatitudeTmp()+","+newLocations.get(i-1).getLongitudeTmp();
			  
	//	  }
		  
		  
		  
		  
		  
		  //-----------------------------------------------------------------------------------//
		  
		  
		  
		  
		  
		  
		  
		  
		  //String origins2[]={" 55.930, -3.118"};
	      //  String destiny2[]={ "50.087, 14"};
		  //DistanceTimeMatriz[] distanceTimeMatriz=null;
		 
		 
//		  DistanceTimeMatrixUtility distanceTimeMatrixUtility = null;
//		  List<LocationTmp> SubListLocation;
//		  
//		 
//		  //String[] destiny = null;
//		  
//		  
//		  for(int i=0; i<MaxConsultGoogle; i++){
//			  
//		  }
		  
		  
//		  for(int i=0; i<newLocations.size()-1; i++){
//			  
//			  for(int j=0; j ){
//				  
//			  }
//		  }
		  
		  
		 ////Condicion de corte de array
//		  if(newLocations.size() > 25){
//			  int contInitLocation=0;
//			  int contFinalLocation=24;
//			  while(newLocations.size() > contFinalLocation ){
//				  SubListLocation = newLocations.subList(contInitLocation, contFinalLocation);
//			  }
//			  
//		  }
		  
		 
		  
		  //System.out.println("::::::::::: en el head: "+((LocationTmp) head).getLocationId()+" id"+((LocationTmp) head).getLocationId()+" ::::::::::::: ");
//		  System.out.println("::::::::::: Imprimiendo el primer objeto de la lista ::::");
//		  System.out.println(ToStringBuilder.reflectionToString(newLocations.get(0), ToStringStyle.MULTI_LINE_STYLE));
//		 
//		  for(int indexForLocationTmp=0; indexForLocationTmp<newLocations.size()-1; indexForLocationTmp++){
//			  List<LocationTmp> TmpNewLocation = newLocations;
//			  LocationTmp NewLocation = newLocations.get(indexForLocationTmp);
//			  LocationTmp restNewLocation = TmpNewLocation.remove(indexForLocationTmp);
//			  
//			 
//			  	
////		  			for(Location loc: oldLocations){
////			  
////		  				//Aseguro que el punto de origen no sea igual al punto de destino
////		  				if(locTmp.getLatitudeTmp() != loc.getLatitude() && locTmp.getLongitudeTmp() != loc.getLongitude()){
////		  					
////		  				}
////		  			}
//		  }
//		  					
//								try {
//		//								String origins[] ={ locTmp.getLatitudeTmp()+","+locTmp.getLongitudeTmp() ,"-33,02 , -70,003" ,"-34,05, -70,005" };
//										String origins[] ={ "-33.415858, -70.600841" , "-33.431582, -70.586795", "-33.433809, -70.612752"};
//										Integer contDistanceDuration=0;
//										
//										String destiny[] ={ loc.getLatitude()+","+loc.getLongitude(), "-34.55, -70.0055","-33.02 , -70.003" };
//										//destiny[i]= loc.getLatitude()+","+loc.getLongitude();
//										
//										DistanceMatrix result = DistanceMatrixApi.getDistanceMatrix(context, origins, origins).await();
//										
//									
//										System.out.println("::::::::::: VIENDO LOS PUNTOS DE ORIGEN QUE ENTOTAL SON: "+result.originAddresses.length+" ::::::::::::: ");
//										
//										for ( String distMat: result.originAddresses){
//											
//											System.out.println("%%::::::::::: "+distMat+" :::::::::::::%% ");
//											
//										}
//										System.out.println("::::::::::: VIENDO LOS PUNTOS DE DESTINO QUE EN TOTAL SON: "+result.destinationAddresses.length+" ::::::::::::: ");
//										
//										for ( String dest: result.destinationAddresses){
//											System.out.println("%%::::::::::: "+dest+" :::::::::::::%% ");	
//										}
//										
//										System.out.println("::::::::::: viendo el result ::::::::::::: ");
//								        System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.MULTI_LINE_STYLE));
//								        System.out.println(":::::::: Viendo que hay en rows ::::::::::");
//								        System.out.println(ToStringBuilder.reflectionToString(result.rows, ToStringStyle.MULTI_LINE_STYLE));
//								        System.out.println("::::::::::::::::::::::::::::------------------------------------::::::::::::::::::::::::::::");
//								        
////								        for(int i=0; i<result.originAddresses.length; i++){
////								        	 System.out.println(":::::::: Viendo que hay en rows con i="+i+" ::::::::::");
////										        System.out.println(ToStringBuilder.reflectionToString(result.rows[i], ToStringStyle.MULTI_LINE_STYLE));
////										        
////										        for(int j = 0; j<result.rows[i].elements.length; j++){
////										        	
////										        	System.out.println(":::::::: Viendo que hay en ELEMENTS con j="+j+" ::::::::::");
////										        		
////										        		System.out.println("distance: "+ result.rows[i].elements[j].distance.inMeters);
////										        		System.out.println("distance: "+ result.rows[i].elements[j].duration.inSeconds);
////										        		
////										        		Long Distance = result.rows[i].elements[j].distance.inMeters;
////										        		Long Duration = result.rows[i].elements[j].duration.inSeconds;
////		//								        		origen=result.rows[i].elements[i].status.
////		//										        Double DistanceDouble = Distance.doubleValue();
////												        Double DurationDouble = Duration.doubleValue();
////												        System.out.println("))))))))((((((((((((:::ESTE ES INDICE "+contDistanceDuration+" :::::))))))))((((((((((((");
////		//										        System.out.println("))))))))((((((((((((:::ESTE ES DistanceDouble "+DistanceDouble+" :::::))))))))((((((((((((");
////		//										        System.out.println("))))))))((((((((((((:::ESTE ES DistanceDouble "+DistanceDouble+" :::::))))))))((((((((((((");
////		//										        System.out.println("))))))))((((((((((((:::ESTE ES DistanceDouble "+DistanceDouble+" :::::))))))))((((((((((((");
////												        
////												        //distanceTimeMatriz.setDistance(DistanceDouble);
////												        //distanceTimeMatriz.setDuration(DurationDouble);
////												        
////												        System.out.println("))))))))((((((((((((::: Listo para Guardar :::::))))))))((((((((((((");
////												        //distanceTimeMatrixUtility.appendDistanceTime(distanceTimeMatriz);
////												        
////												        
////												        
////		//								        	  System.out.println(ToStr	ingBuilder.reflectionToString(result.rows[i].elements[j], ToStringStyle.MULTI_LINE_STYLE));
////												        contDistanceDuration++;
////										        }
////								        }
//								        //System.out.println(result.rows[0].elements[0].distance.inMeters);
//								       // distanceTimeMatriz[i].setEnd(loc.getLocationId());
//									
//								} catch (Exception e) {
//									
//									e.printStackTrace();
//									return null;
//									
//								}
//		  				}else{
//		  					System.out.println("Punto de Origen igual al punto de Destino, la busqueda de distancia no es realizada");
//		  					
//		  				}
//				   
//			   	}
		  		
			  
			  
//		  }
		
		
		return distanceTimeMatrixUtility;       
	
	}

	@Override
	public void getTimeByGoogle() {
		// TODO Auto-generated method stub
//		return null;
	}

}
