package cl.citymovil.route_pro.message_listener.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;

import cl.citymovil.route_pro.message_listener.domain.DistanceTimeMatriz;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.LocationTmp;
import cl.citymovil.route_pro.solver.util.DistanceTimeMatrixUtility;
import cl.citymovil.route_pro.solver.util.LocationContainer;



@Service
public class AskToGoogleImpl implements AskToGoogle{
	
	 protected final Log logger = LogFactory.getLog(getClass());
	
private static int MaxConsultGoogle = 25;

@Autowired
DistanceTimeMatrixUtility distanceTimeMatrixUtility;
	
	@SuppressWarnings("null")
	@Override
	public  List <DistanceMatrix> getDistanceByGoogle(LocationContainer locationContainer){//String[] newLocations,String[] oldLocations) {
		  //GeoApiContext context = new GeoApiContext().setApiKey("j61PmiK8glmpGIVYL47RQrm_zbKk=");//"AIzaSyB-ZZHRgGvMLczqzDZnmFBds4Zs27wm1AY");//AIzaSyBc-yEd3hfr4Q9GEVf2uYu_JaGbtLNlt7Y
		   GeoApiContext context = new GeoApiContext().setEnterpriseCredentials("gme-bigservicespacityplanning", "j-NB2bOlmRUMInQ459WVwrf7O9w=");
		  
		  //1- necesito ingresar las nueva locaciones a la tabla de location para obtener su id
		  
		  //2-ordenar todos las locaciones en un nuevo arreglo
		  
		  //3- dividir ese arreglo en secciones de 25 string[]
		  
		  //4-Consultar al Google
		  
		  //5-Retornar un array con todos los nuevos puntos y pasarlos al postProcess
		  
		  //-----------------------------------------------------------------------------------//
		  logger.info("blablabalbalbalbalbalbalab");
		  List<Location>  oldLocations= locationContainer.getLocation();
		  List<LocationTmp> newLocations = locationContainer.getLocationTmp();
		  Integer sizeNewLocation = newLocations.size();
		  Integer sizeOldLocation = oldLocations.size();
		  int contDeConsultas=0;
		  int contDeArreglos=0;
		  LocationTmp cabeza;
		  Location cabezaOld;
		  //for(int contNewLoc=0; contNewLoc < sizeNewLocation; contNewLoc++){
			int contNewLoc=0;
		  String newPosition[] = {newLocations.get(contNewLoc).getLatitudeTmp()+","+newLocations.get(contNewLoc).getLongitudeTmp()};
			 // for(int contOldLoc=0; contOldLoc < sizeOldLocation; contOldLoc++){
			  int contOldLoc=0;
				  String oldPosition[] ={ oldLocations.get(contOldLoc).getLatitude()+","+oldLocations.get(contOldLoc).getLongitude()};
				  
				  
				  System.out.println(":::::::::PREGUNSTANDO A GOOGLE:::::::::");
				  	try {
				  		  DistanceMatrix result = DistanceMatrixApi.getDistanceMatrix(context, newPosition, oldPosition).await();
				  		  
				  		  System.out.println("::Origen::"+result.destinationAddresses[0]);
				  		System.out.println("::Destino::"+result.destinationAddresses.toString());
				  		System.out.println("::cantidad de Elementos::"+result.rows[0].elements.length);
				  		  
				  	
				  	} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  
			 // }
			  
			  
		 // }
		  
		  //----------------------------------FIN-----------------------//
		  
//		  List <String[]> originsArrayList = new ArrayList <String[]>();
//		  List <String[]> destinyArrayList = new ArrayList <String[]>();
//		  List <String> origins = new ArrayList<String>();
//		  String[] originsArray=new String[]{};
//		  String[] destinysArray=new String[]{};
//		  List <String> destiny = new ArrayList<String>();
		  
		  
		  
		  
//		  boolean flagRevisionArrayIncompletos=true;
//		  if(SizeNewLocation>25){
//		  for(int i=0; i< SizeNewLocation; i++){
//			  cabeza = newLocations.get(i);
//			  //cola= newLocations.subList(i+1, SizeNewLocation);
//			  String newPosition=cabeza.getLatitudeTmp()+","+cabeza.getLongitudeTmp();
//			  
//			  for(int j=0; j<SizeOldLocation; j++){
//				  cabezaOld = oldLocations.get(j);
//				 // colaOld= oldLocations.subList(j+1, SizeOldLocation);
//				  System.out.println("::: ESTO ES LO QUE ENCUENTRO EN ORIGINS PREVIO A LA CAIDA "+cabeza.getLatitudeTmp()+","+cabeza.getLongitudeTmp()+"::::");
//				  System.out.println("::: ESTO ES LO QUE ENCUENTRO EN ORIGINS PREVIO A LA CAIDA "+cabezaOld.getLatitude()+","+cabezaOld.getLongitude()+"::::");
//				  System.out.println("::: CONTADORES I:"+i+":: J:"+j+"::");
//				  
//				 
//				  String oldPosition=cabezaOld.getLatitude()+","+cabezaOld.getLongitude();
//				  
//				  destiny.add(oldPosition);
//				  origins.add(newPosition);//cabeza.getLatitudeTmp().toString()+","+cabeza.getLongitudeTmp().toString()
//				  
//				  if(contDeConsultas+1==MaxConsultGoogle){
//					  origins.toArray(originsArray);
//					  destiny.toArray(destinysArray);
//					  originsArrayList.add(originsArray);
//					  destinyArrayList.add(destinysArray);
//					  
//					  //String[] OriginsStringArray = new String[origins.size()];
//					  //origins.toArray();
//					 
//					  //originsArrayList.add(contDeArreglos, OriginsStringArray);
//					  
//					  //String[] DestinysStringArray = new String[destiny.size()];
//					  //destiny.toArray(DestinysStringArray);
//					  //destinyArrayList.add(contDeArreglos, DestinysStringArray);
//					  
//					  contDeArreglos++;
//					  contDeConsultas=0;
//					  origins.clear();
//					  destiny.clear();
//					  flagRevisionArrayIncompletos=false;
//				  }else{
//					  flagRevisionArrayIncompletos=true;
//					  contDeConsultas++;
//				  }
//				  
//				  
//			  }
//			  
//		  }
//		  }
//		  
//		  
//		  if(flagRevisionArrayIncompletos==true){
//			  System.out.println("::::: aqui estoy en flagRevisionArrayIncompletos:::::");
//			  
//			 
//			  
//			  destiny.toArray(destinysArray);
//			  origins.toArray(originsArray);
//			  
//			  originsArrayList.add(contDeConsultas,originsArray);
//			  destinyArrayList.add(contDeConsultas,destinysArray);
//			  
//			  contDeConsultas++;
//			  
//		  }
//		  
//		  System.out.println("<<<<<<<<::::::::: LISTO PARA MOSTRAR LOS ARRAYS CREADOS originsArrayList.size()= "+originsArrayList.size()+"::::::::::>>>>>>>>");
//		  List <DistanceMatrix> arrayDistanceMatrixResult = new ArrayList<DistanceMatrix>();
//		  
//		  for(int contGoogleAsk=0; contGoogleAsk < originsArrayList.size(); contGoogleAsk++) {
//			  System.out.println(":::::::::CONT(contGoogleAsk):"+contGoogleAsk+"::(originsArrayList.get(contGoogleAsk).length)"+originsArrayList.get(contGoogleAsk).length+"::::::::");
//			  for(int f=0; f< originsArrayList.get(contGoogleAsk).length; f++){
//				  System.out.println("::::::::Origen:  "+ originsArrayList.get(contGoogleAsk)[f]+"  Destino: "+destinyArrayList.get(contGoogleAsk)[f]+":::::::::");
//				   
//			  }
			  
//			  	try {
//			  		 arrayDistanceMatrixResult.add(contGoogleAsk, DistanceMatrixApi.getDistanceMatrix(context, originsArrayList.get(contGoogleAsk), destinyArrayList.get(contGoogleAsk)).await());
//			  		 System.out.println(":::::MOSTRANDO EL RESULTADO DE GOOGLE::::");
//			  		 for(int x=0; x< arrayDistanceMatrixResult.get(contGoogleAsk).rows.length; x++){
//			  			System.out.println("Cantidad de rows: "+arrayDistanceMatrixResult.get(contGoogleAsk).rows+" Cantidad de Elementos en el rows numero "+x+"(y): "+arrayDistanceMatrixResult.get(contGoogleAsk).rows[x].elements.length);
//			  			for(int y=0; y< arrayDistanceMatrixResult.get(contGoogleAsk).rows[x].elements.length; y++){
//			  				System.out.println("Entregando informacion arrojada por google de los elementos");
//			  				
//			  				DistanceMatrixElement elemet = arrayDistanceMatrixResult.get(contGoogleAsk).rows[x].elements[y];
//			  				System.out.println("elemento y="+y+": y su contenido es: distancia-"+elemet.distance+" duracion-"+elemet.duration+" status-"+elemet.status);
//			  				
//			  			}
//			  		 }
//			  		System.out.println("");
//			  	} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		  }
		  
		  /*
		  for(int cont=0; cont <= originsArrayList.size(); cont++) {
			  System.out.println(":::::::::CONT:"+cont+"::::::::::");
			  	try {
			  		 arrayDistanceMatrixResult.add( DistanceMatrixApi.getDistanceMatrix(context, originsArrayList.get(cont), destinyArrayList.get(cont)).await());
			  	
			  	} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		  }
		  */
		  
		  
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
		
		
		return null;//arrayDistanceMatrixResult;       
	
	}

	@Override
	public void getTimeByGoogle() {
		// TODO Auto-generated method stub
//		return null;
	}

}
