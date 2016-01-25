package cl.citymovil.route_pro.message_listener.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;

import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.LocationTmp;
import cl.citymovil.route_pro.solver.util.DistanceTimeMatrixUtility;
import cl.citymovil.route_pro.solver.util.LocationContainer;
import cl.citymovil.route_pro.solver.util.RelationLocation;



@Service
public class AskToGoogleImpl implements AskToGoogle{
	
	 protected final Log logger = LogFactory.getLog(getClass());
	
	 protected final int MaxConsultGoogle = 25;

@Autowired
DistanceTimeMatrixUtility[] matrizArrayOrigDestTimeDur;


	

	@Override
	public  DistanceTimeMatrixUtility[] getDistanceByGoogle(LocationContainer locationContainer){//String[] newLocations,String[] oldLocations) {
		  //GeoApiContext context = new GeoApiContext().setApiKey("j61PmiK8glmpGIVYL47RQrm_zbKk=");//"AIzaSyB-ZZHRgGvMLczqzDZnmFBds4Zs27wm1AY");//AIzaSyBc-yEd3hfr4Q9GEVf2uYu_JaGbtLNlt7Y
		Date d = new Date();
		System.out.println("Tiempo Ahora:"+d.toString());
		GeoApiContext context = new GeoApiContext().setEnterpriseCredentials("gme-bigservicespacityplanning", "j-NB2bOlmRUMInQ459WVwrf7O9w=");
		  
		  //1- necesito ingresar las nueva locaciones a la tabla de location para obtener su id
		  
		  //2-ordenar todos las locaciones en un nuevo arreglo
		  
		  //3- dividir ese arreglo en secciones de 25 string[]
		  
		  //4-Consultar al Google
		  
		  //5-Retornar un array con todos los nuevos puntos y pasarlos al postProcess
		  
		  //-----------------------------------------------------------------------------------//
		  
		   //obtencion de id de origen y destino.
		   /*
		    * El almacenamiento de origen y destino se realizan en un (List <List <Long[]> >), donde el primer List almacena
		    * hasta 25 pares de Long que registran los Id de Origen y destino respectivamente.
		    * 
		    * Nombre de las variables:
		    * (List <List <Long[]> > listContainerOfListOrigDest)
		    */
		////////////////////Variables definitivas//////////////////
		
		ArrayList <ArrayList<RelationLocation>> listOfListRelationLocationsGoogle= new ArrayList <ArrayList <RelationLocation >>();
		ArrayList <RelationLocation> listRelationLocationWithMaxLength =new ArrayList <RelationLocation>();
		ArrayList <RelationLocation> listRelationLocationDeRetorno =new ArrayList <RelationLocation>();
		  List<Location>  oldLocations= locationContainer.getLocation();
		  List<LocationTmp> newLocations = locationContainer.getLocationTmp();
		  Integer sizeNewLocation = newLocations.size();
		  Integer sizeOldLocation = oldLocations.size();
		  int countOfLocation=1;
		  int countOfQueryGoogle=0;
		  
		  List <String> newPositionStringList=new ArrayList <String>();
		  List <String> oldPositionStringList=new ArrayList <String>();
		  
		  //Variables empleadas en Id
		  Long idPositionOrigin;
		  Long idPositionDestiny;
//		  ArrayList <Long[]> idPositionLatLng= new ArrayList <Long[]>();
//		  List <Long[]> idOriginDestino = new ArrayList <Long[]>();
//		  List < List <Long[]>> listContainerOfListOrigDest= new ArrayList <List <Long[]>>();
		  ///////////////////////////
		  
		////////////////////FIN Variables definitivas//////////////////
		 
		  String positionStringNew=new String();
		  
		 
		  String[] origenPosition;
		  String[] destinyPosition;
		 
		  List <String[]> origenLocationsList= new ArrayList <String[]>();
		  List <String[]> destinyLocationsList= new ArrayList <String[]>();
		 // List <Long[]> idLocationsList= new ArrayList <Long[]>();
		  Boolean flag=true;
		 // List <Long[]> matrix=new ArrayList <Long[]>();
		  logger.info(destinyLocationsList);
		  
		  
		  /********* DESARROLLO*************/
		  
		  //obtengo una nueva locacion
		  for(int contNewLoc=0; contNewLoc < sizeNewLocation; contNewLoc++){
			//obtengo una posicion nueva y despues de eso genero todas el arreglo de consultas hasta completar un array de largo 25,
			  //si el array no llega a 25, es copiado entero en el sgte paso gracias a un flag que identifica esta situacion.
			 
			  /** GENERACION DE LIST ORIGEN **/
				//ORIGEN
				newPositionStringList.add(positionStringNew);
				origenPosition = newPositionStringList.toArray(new String[newPositionStringList.size()]);
				origenLocationsList.add(countOfQueryGoogle, origenPosition);
				
				
				
			  positionStringNew=newLocations.get(contNewLoc).getLatitudeTmp()+","+newLocations.get(contNewLoc).getLongitudeTmp();
			  
				Double[] originPositionLatLng = new Double[2];
				originPositionLatLng[0]=newLocations.get(contNewLoc).getLatitudeTmp();
				originPositionLatLng[1]=newLocations.get(contNewLoc).getLongitudeTmp();
				idPositionOrigin=newLocations.get(contNewLoc).getLocationId();
			 
			  /********LO USO???*/
			 // LocationTmp originPosition = newLocations.get(contNewLoc);
			 
			 
			 
			 //genero la lista de consulta con entre la nueva locacion y las que ya estan
			for(int contOldLoc=0; contOldLoc < sizeOldLocation; contOldLoc++){
				
				RelationLocation relationLocation=new RelationLocation();
				
				Double[] destinyPositionLatLng = new Double[2];
				destinyPositionLatLng[0]=oldLocations.get(contOldLoc).getLatitude();
				destinyPositionLatLng[1]=oldLocations.get(contOldLoc).getLongitude();
				idPositionDestiny=oldLocations.get(contOldLoc).getLocationId();
				
				
				
				/**Almacenamiento en relationLocation*/
				//almacenamiento en un relationLocation de la posicion de origen, destino, los id respectivos y 
				//la distancia y duracion entre cada uno en ambas direcciones
				/**LAT - LONG - ID DEL ORIGEN*/
				relationLocation.setFirstLocation(originPositionLatLng);
				relationLocation.setIdFirstLocation(idPositionOrigin);
				/**LAT - LONG - ID DEL DESTINO*/
				relationLocation.setSecondLocation(destinyPositionLatLng);
				relationLocation.setIdSecondLocation(idPositionDestiny);
				
				
				
				/**ALMACENAMIENTO EN EL LIST RELATIONLOCATION**/
				System.out.println("************   countOfLocation:"+countOfQueryGoogle+"----- countOfLocation:"+countOfLocation);
				listRelationLocationWithMaxLength.add(relationLocation);
				//listRelationLocation.get(countOfQueryGoogle).add(countOfLocation-1, relationLocation);
				
				/**GENERACION DE LIST PARA CONSULTA A GOOGLE**/
				
				//DESTINO
				oldPositionStringList.add(contOldLoc,oldLocations.get(contOldLoc).getLatitude()+","+oldLocations.get(contOldLoc).getLongitude());
				
				
				
			    //almacenamiento respectivo de id origen e id destino
			  //  idPositionLatLng.add(contDeConsultas, idOriginDestino);
			    
				//creado el destino y origen para la primera consulta, se aumenta el contador de consultas, que hace de contador de largo del arreglo
				countOfLocation++;
				/**EMPAQUETADO PARA CONSULTA A GOOGLE CON ARRAY LONG DE LARGO=MaxConsultGoogle**/
				//verifico si se ha llegado al largo de 25 	
				if(countOfLocation==MaxConsultGoogle){//newPositionStringList.size()==25
					
					//transformo los List <String> a String[]
					destinyPosition = oldPositionStringList.toArray(new String[oldPositionStringList.size()]);
					
					//almaceno los String[25] en un List <String[]> que luego utilizaré para consultar a google
					//origenLocationsList.add(countOfQueryGoogle, origenPosition);
					destinyLocationsList.add(countOfQueryGoogle, destinyPosition);	
					
					 /**AlMACENAMIENTO DE LAS LOCACIONES EN LA LISTA DE LISTAS DE RELACIONES DE LOCACIÓN DE LARGO=MaxConsultGoogle**/
					listOfListRelationLocationsGoogle.add(countOfQueryGoogle, listRelationLocationWithMaxLength);
					//listContainerOfListOrigDest.add(contDeArreglos, idPositionLatLng);
					
					//newPositionStringList.clear();
					//oldPositionStringList.clear();
					//idPositionLatLng.clear();
					//idLocationsList.clear();
					
					//modificacion de variable post if
					listRelationLocationWithMaxLength=new ArrayList <RelationLocation>();
					countOfLocation=0;
					countOfQueryGoogle++;
					flag=false;
					
				}else{
					flag=true;
				}
			}
		  }
		  /**EMPAQUETADO PARA CONSULTA A GOOGLE CON ARRAY LONG DE LARGO  < MaxConsultGoogle**/
		  if(flag==true  ) {//&& (newPositionStringList.size() == oldPositionStringList.size() ) 
			 System.out.println("INGRESO A EMPAQUETADO LARGO  < MaxConsultGoogle");
			  /**AlMACENAMIENTO DEL RESTO DE LAS LOCACIONES EN LA LISTA DE LISTAS DE RELACIONES DE LOCACIÓN**/
			  listOfListRelationLocationsGoogle.add(countOfQueryGoogle, listRelationLocationWithMaxLength);
			  
			  origenPosition = newPositionStringList.toArray(new String[newPositionStringList.size()]);
			  destinyPosition = oldPositionStringList.toArray(new String[oldPositionStringList.size()]);
			  
			 // idPositionLatLng.add(contDeConsultas, idOriginDestino);
			  
			  
			  origenLocationsList.add(countOfQueryGoogle, origenPosition);
			  
			  destinyLocationsList.add(countOfQueryGoogle, destinyPosition);	
			  
			 // idLocationsList.add(contDeArreglos, idOriginDestino);
			  
			  //listContainerOfListOrigDest.add(countOfQueryGoogle, idPositionLatLng);
			  
			  
		  }
		  
		  
			//String newPosition[] = {newLocations.get(contNewLoc).getLatitudeTmp()+","+newLocations.get(contNewLoc).getLongitudeTmp()};
		 
		  //FUNCIONA String[] newPosition = newPositionStringList.toArray(new String[newPositionStringList.size()]);
		   
		   //String[] strarray = strlist.toArray(new String[0]);
		   //PROCESO INVERSO, PASAR DE STRING[] A LIST <STRING>:-> Arrays.toString(strarray)
		  
		  System.out.println("Tamaño origenLocationsList.size():"+origenLocationsList.size());
		  System.out.println("Tamaño destinyLocationsList.size():"+destinyLocationsList.size());
		 // System.out.println("Tamaño listRelationLocation.get(countOfQueryGoogle).size():"+listRelationLocation.get(countOfQueryGoogle).size());
		   //DistanceTimeMatrixUtility[] matrizArrayOrigDestTimeDur= new DistanceTimeMatrixUtility[contDeArreglos+1];
		 
				 // String oldPosition[] ={ oldLocations.get(contOldLoc).getLatitude()+","+oldLocations.get(contOldLoc).getLongitude()};
 
				 System.out.println(":::::::::PREGUNTANDO A GOOGLE:::::::::");
			
				 	/****   CONSULTANDO A GOOGLE    ****/	 
				  	try {
				  		
				  		//PARA CADA QUERYGOOGLE DE TAMAÑO MAXIMO DE 25
				  		 for(int count1=0;  count1 < countOfQueryGoogle ; count1++){
				  			 
				  			System.out.println(":::::::::: Leyendo lo que estoy enviando a google::::::::::::::::");
				  			
				  			for(int cont1=0; cont1 < origenLocationsList.get(count1).length; cont1++ ){
				  				System.out.println(":::::"+cont1+") origen"+origenLocationsList.get(count1)[cont1]);
				  				
				  			}
				  			for(int cont2=0; cont2 < destinyLocationsList.get(count1).length; cont2++){
				  				System.out.println(":::::"+cont2+") destino"+destinyLocationsList.get(count1)[cont2]);
			  				}
				  			 
					  		System.out.println("::::Contador:"+count1);
					  		System.out.println("::::Tamaño origenLocationsList.get(contOldLoc).length:"+origenLocationsList.get(count1).length);
							System.out.println(":::Tamaño destinyLocationsList.get(contOldLoc).length:"+destinyLocationsList.get(count1).length);
							System.out.println(":::Tamaño listOfListRelationLocationsGoogle.get(count1).size():"+listOfListRelationLocationsGoogle.get(count1).size());
					  		DistanceMatrix result = DistanceMatrixApi.getDistanceMatrix(context, origenLocationsList.get(count1), destinyLocationsList.get(count1) ).await();
					  		
					  		System.out.println("result: "+ result);
					  		
					  		/**COMPLETANDO EL RelationLocation con los DATOS DE GOOGLE**/
					  		
					  		System.out.println("listOfListRelationLocationsGoogle.get(count1).size(): "+ listOfListRelationLocationsGoogle.get(count1).size());
					  		for(int count2=0; count2 < result.rows.length; count2++ ){
					  			
					  			System.out.println("******* count2 :"+count2+"********");
					  			System.out.println("******* result.destinationAddresses->count2 :"+result.destinationAddresses[count2]+"********");
//					  			System.out.println("******* result.originAddresses->count2 :"+result.originAddresses[count2]+"********");
					  			System.out.println("******* result.destinationAddresses.length :"+result.destinationAddresses.length+"********");
					  			System.out.println("******* result.originAddresses.length:"+result.originAddresses.length+"********");
					  			
					  			
					  			System.out.println("result.originAddresses.length : "+ result.originAddresses.length );
					  			for(int count3=0; count3 < result.rows[count2].elements.length; count3++){//result.rows[count2].elements.length
					  				DistanceMatrixElement[] resultados = result.rows[count2].elements;
					  				
					  				long distanceResult = resultados[count3].distance.inMeters;
					  				Double distance = (double) result.rows[count2].elements[count3].distance.inMeters;
					  				//result.originAddresses
					  				System.out.println("///////////////");
					  				System.out.println("/****/result.rows[count2].elements.length :"+result.rows[count2].elements.length);
					  				System.out.println("/****/distanceResult :"+distanceResult);
					  				System.out.println("count2 :"+count2+"-count3 :"+count3+"*******Distance :"+distance+"********");
					  				System.out.println("///////////////");

					  				
					  				/*
 * 
 * 
 *  var origins = response.originAddresses;
        var destinations = response.destinationAddresses;
 
   for (var i = 0; i < origins.length; i++) {
            var results = response.rows[i].elements;
                console.log(results[i].distance.text);
                document.getElementById('ctl00_MainContent_hidSpeed').value += results[i].distance.text+"&";
        }
}
 
 
 * 
 */
					  				//listOfListRelationLocationsGoogle.get(count1).get(count2).setGoingDuration(Distance);
					  				
					  				
					  			//AQUI VOY, FALTA GENERAR LAS LISTA DE RELATIONLOCATION EN UN LIST UNICO QUE SEA MANDADO COMO RETORNO
					  				//PARA QUE LO TOME PROSTPROCESS Y LO ALMACENE EN UNA BASE DE DATOS
					  			}
					  			
					  			
					  		}
//					  		matrizArrayOrigDestTimeDur[count].setDistanceMatrix( result);
					  		
					  		matrizArrayOrigDestTimeDur[count1].setOrigen(origenLocationsList.get(count1));
					  		matrizArrayOrigDestTimeDur[count1].setDestiny(destinyLocationsList.get(count1));
					  		//matrizArrayOrigDestTimeDur[count].setIdOriginDestiny(listContainerOfListOrigDest.get(countOfLocation));
					  		
//					  		System.out.println("::::::::Id de origen:"+matrizArrayOrigDestTimeDur[count].getIdOriginDestiny()[0]+":::::::::");
//					  		System.out.println("::::::::Id de destino:"+matrizArrayOrigDestTimeDur[count].getIdOriginDestiny()[1]+":::::::::");


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
		  
				  	Date d1 = new Date();
					System.out.println("Tiempo Ahora:"+d1.toString());
				
		return matrizArrayOrigDestTimeDur;//arrayDistanceMatrixResult;       
	}

	@Override
	public void getTimeByGoogle() {
		// TODO Auto-generated method stub
//		return null;
	}

}
