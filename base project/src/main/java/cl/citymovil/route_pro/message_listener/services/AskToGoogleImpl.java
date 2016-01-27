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

import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.LocationTmp;
import cl.citymovil.route_pro.solver.util.DistanceTimeMatrixUtility;
import cl.citymovil.route_pro.solver.util.LocationContainer;
import cl.citymovil.route_pro.solver.util.RelationLocation;

@Service
public class AskToGoogleImpl implements AskToGoogle {

	protected final Log logger = LogFactory.getLog(getClass());

	protected final int MaxConsultGoogle = 26;

	@Autowired
	DistanceTimeMatrixUtility[] matrizArrayOrigDestTimeDur;

	@Override
	public ArrayList<RelationLocation> getDistanceByGoogle(LocationContainer locationContainer) {// String[]
																									// newLocations,String[]
																									// oldLocations)
																									// {
	//	GeoApiContext context = new GeoApiContext().setApiKey("j61PmiK8glmpGIVYL47RQrm_zbKk=");//"AIzaSyB-ZZHRgGvMLczqzDZnmFBds4Zs27wm1AY");//AIzaSyBc-yEd3hfr4Q9GEVf2uYu_JaGbtLNlt7Y
		Date d = new Date();
		System.out.println("****************************Tiempo Ahora:" + d.toString());
		logger.info("hora: "+ d.toString());
		GeoApiContext context = new GeoApiContext().setEnterpriseCredentials("gme-bigservicespacityplanning","j-NB2bOlmRUMInQ459WVwrf7O9w=");

		// 1- necesito ingresar las nueva locaciones a la tabla de location para
		// obtener su id

		// 2-ordenar todos las locaciones en un nuevo arreglo

		// 3- dividir ese arreglo en secciones de 25 string[]

		// 4-Consultar al Google

		// 5-Retornar un array con todos los nuevos puntos y pasarlos al
		// postProcess

		// -----------------------------------------------------------------------------------//

		// obtencion de id de origen y destino.
		/*
		 * El almacenamiento de origen y destino se realizan en un (List <List
		 * <Long[]> >), donde el primer List almacena hasta 25 pares de Long que
		 * registran los Id de Origen y destino respectivamente.
		 * 
		 * Nombre de las variables: (List <List <Long[]> >
		 * listContainerOfListOrigDest)
		 */
		//////////////////// Variables definitivas//////////////////

//		ArrayList<ArrayList<RelationLocation>> listOfListRelationLocationsGoogle = new ArrayList<ArrayList<RelationLocation>>();
		ArrayList<RelationLocation> listRelationLocationOrigin = new ArrayList<RelationLocation>();
		
		ArrayList<RelationLocation> listRelationLocationDeRetorno = new ArrayList<RelationLocation>();
		List<Location> oldLocations = locationContainer.getLocation();

		// Location newDestiny= new Location();

		List<LocationTmp> newLocations = locationContainer.getLocationTmp();

		/** TRATAMIENTO DE LAS NUEVAS LOCACIONES **/
		/**
		 * 1- INGRESAR LAS NUEVAS LOCATIONES A LA BASE DE DATOS, PARA PODER
		 * OBTENER SU ID
		 * 
		 * 2- TRANSFORMAR LAS NUEVAS LOCATION DE ORIGEN DE LOCATION_TMP->ORIGEN
		 * A LOCATION->DESTINO AGREGANDO EL ID
		 * 
		 * 3- PROCESAR LOS LOCATION->DESTINO CON NORMALIDAD.
		 * 
		 * 
		 **/

		Integer sizeNewLocation = newLocations.size();
		Integer sizeOldLocation = oldLocations.size();
		ArrayList<DistanceMatrix> resultsList = new ArrayList<DistanceMatrix>();
//		System.out.println("@@@--- sizeNewLocation: " + sizeNewLocation + " sizeOldLocation:" + sizeOldLocation);
		int countOfLocation = 1;
		int countOfQueryGoogle = 0;
		int countOfLocationOrigin = 1;
		int countOfQueryGoogleOrigin = 0;
		ArrayList <ArrayList <Location>> listOfListLocationOrigins = new ArrayList <ArrayList <Location>>();
		ArrayList <ArrayList <Location>> listOfListLocationDestiny = new ArrayList <ArrayList <Location>>();
		ArrayList <Location> listOfOriginsLocation = new ArrayList <Location>();
		ArrayList <Location> listOfDestinyLocation = new ArrayList <Location>();
		
		List<String> newPositionStringList = new ArrayList<String>();
		List<String> oldPositionStringList = new ArrayList<String>();
		// Variables empleadas en Id
		Long idPositionOrigin;
		Long idPositionDestiny;
		//////////////////// FIN Variables definitivas//////////////////
		String positionStringNEW = new String();
		String positionStringOLD = new String();
		String[] origenPosition;
		String[] destinyPosition;
		List<String[]> origenLocationsList = new ArrayList<String[]>();
		List<String[]> destinyLocationsList = new ArrayList<String[]>();
		// List <Long[]> idLocationsList= new ArrayList <Long[]>();
		Boolean flag = true;
		Boolean flagOrigins = true;

		/********* DESARROLLO *************/

		/** PROCESAMIENTO DE LOCATION ORIGINS **/
		// Genero todos los ORIGENES en array de maximo 25
		for (int contNewLoc = 0; contNewLoc < sizeNewLocation; contNewLoc++) {
			Double[] originPositionLatLng = new Double[2];
			
			//RelationLocation relationLocation = new RelationLocation(); 
			/**
			 * DATOS OBTENIDOS DEL PROCESO ANTERIOR PARA GENERAR EL
			 * RELATIONLOCATION
			 **/
//			originPositionLatLng[0] = newLocations.get(contNewLoc).getLatitudeTmp();
//			originPositionLatLng[1] = newLocations.get(contNewLoc).getLongitudeTmp();
//			idPositionOrigin = newLocations.get(contNewLoc).getLocationId();
			Location originLocation = new Location(); 
			originLocation.setLatitude(newLocations.get(contNewLoc).getLatitudeTmp());
			originLocation.setLongitude(newLocations.get(contNewLoc).getLongitudeTmp());
			originLocation.setLocationId(newLocations.get(contNewLoc).getLocationId());
			listOfOriginsLocation.add(contNewLoc, originLocation);
			
//			relationLocation.setIdFirstLocation(idPositionOrigin);
//			listRelationLocationOrigin.add(contNewLoc, relationLocation);

			/** DATOS PARA GENERAR LA CONSLUTA DE GOOGLE */
			// RECOPILO LOS DATOS DE LAT Y LNG, LOS ALMACENO EN UN LIST Y
			// POSTERIORMENTE LOS TRANSFORMO EN UN ARRAY
			// DE ACUERDO A LO REQUERIDO POR GOOGLE
			positionStringNEW = newLocations.get(contNewLoc).getLatitudeTmp() + ","
					+ newLocations.get(contNewLoc).getLongitudeTmp();
			newPositionStringList.add(positionStringNEW);
			origenPosition = newPositionStringList.toArray(new String[newPositionStringList.size()]);
//			System.out.println("origenLocationsList countOfQueryGoogleOrigin: " + countOfQueryGoogleOrigin);

			if (countOfLocationOrigin >= MaxConsultGoogle) {// si hay mas de 25
															// locaciones de origen
				
				/**Almacenamiento de list de list Location para generar el RelationLocation**/
				listOfListLocationOrigins.add(countOfQueryGoogleOrigin, listOfOriginsLocation);
				
				
				/**almacenamiento de los origen list Location que contiene los string que realizan la consulta a Google**/
				origenLocationsList.add(countOfQueryGoogleOrigin, origenPosition);
				countOfLocationOrigin = 1;
				countOfQueryGoogleOrigin++;
				flagOrigins = false;

			} else {
				flagOrigins = true;
			}
			countOfLocationOrigin++;
		}

		if (flagOrigins == true) {
			
			listOfListLocationOrigins.add(countOfQueryGoogleOrigin, listOfOriginsLocation);
			
			origenPosition = newPositionStringList.toArray(new String[newPositionStringList.size()]);
			origenLocationsList.add(countOfQueryGoogleOrigin, origenPosition);
		}

		/**
		 * Leyendo DATOS PARA GENERAR LA CONSLUTA DE GOOGLE BORRAR ESTO DESPUES
		 */
//		System.out.println("origenLocationsList.size()" + origenLocationsList.size());
//		for (int i = 0; i < origenLocationsList.size(); i++) {
//
//			System.out.println("origenLocationsList.get(i).length" + origenLocationsList.get(i).length);
//			for (int j = 0; j < origenLocationsList.get(i).length; j++) {
//				System.out.println("origenLocationsList.get(i)" + origenLocationsList.get(i)[j]);
//			}
//		}

		/** PROCESAMIENTO DE LOCATION DESTINY **/
		// genero la lista de consulta con entre la nueva locacion y las que ya
		// estan
		for (int contOldLoc = 0; contOldLoc < sizeOldLocation; contOldLoc++) {
			// ** RelationLocation relationLocation=new RelationLocation();
			Double[] destinyPositionLatLng = new Double[2];

			/**
			 * DATOS OBTENIDOS DEL PROCESO ANTERIOR PARA GENERAR EL
			 * RELATIONLOCATION
			 **/
//			destinyPositionLatLng[0] = oldLocations.get(contOldLoc).getLatitude();
//			destinyPositionLatLng[1] = oldLocations.get(contOldLoc).getLongitude();
//			idPositionDestiny = oldLocations.get(contOldLoc).getLocationId();
			
			Location destinyLocation = new Location(); 
			
			destinyLocation.setLatitude(oldLocations.get(contOldLoc).getLatitude());
			destinyLocation.setLongitude(oldLocations.get(contOldLoc).getLongitude());
			destinyLocation.setLocationId(oldLocations.get(contOldLoc).getLocationId());
			listOfDestinyLocation.add(contOldLoc, destinyLocation);

			/** DATOS PARA GENERAR LA CONSLUTA DE GOOGLE */
			// RECOPILO LOS DATOS DE LAT Y LNG, LOS ALMACENO EN UN LIST Y
			// POSTERIORMENTE LOS TRANSFORMO EN UN ARRAY
			// DEACUERDO A LO REQUERIDO POR GOOGLE

			positionStringOLD = oldLocations.get(contOldLoc).getLatitude() + ","
					+ oldLocations.get(contOldLoc).getLongitude();
			oldPositionStringList.add(positionStringOLD);
			destinyPosition = oldPositionStringList.toArray(new String[oldPositionStringList.size()]);
			
			
//			System.out.println("oldPositionStringList countOfQueryGoogle: " + countOfQueryGoogle);

			/** Almacenamiento en relationLocation */
			// almacenamiento en un relationLocation de la posicion de origen,
			// destino, los id respectivos y
			// la distancia y duracion entre cada uno en ambas direcciones
			/** LAT - LONG - ID DEL ORIGEN */
			// relationLocation.setFirstLocation(originPositionLatLng);
			// relationLocation.setIdFirstLocation(idPositionOrigin);
			/** LAT - LONG - ID DEL DESTINO */
			// ** relationLocation.setSecondLocation(destinyPositionLatLng);
			// ** relationLocation.setIdSecondLocation(idPositionDestiny);
			/** ALMACENAMIENTO EN EL LIST RELATIONLOCATION **/
			// ** listRelationLocationWithMaxLength.add(relationLocation);
			/** GENERACION DE LIST PARA CONSULTA A GOOGLE **/
			// DESTINO

			/**
			 * EMPAQUETADO PARA CONSULTA A GOOGLE CON ARRAY LONG DE
			 * LARGO=MaxConsultGoogle
			 **/
			// verifico si se ha llegado al largo de 25
			if (countOfLocation >= MaxConsultGoogle) {// newPositionStringList.size()==25
				
				/**Almacenamiento de list de list Location para generar el RelationLocation**/
				listOfListLocationDestiny.add(countOfQueryGoogle, listOfDestinyLocation);
				
				
				
				// destinyPosition = oldPositionStringList.toArray(new
				// String[oldPositionStringList.size()]);
				destinyLocationsList.add(countOfQueryGoogle, destinyPosition);
				/**
				 * AlMACENAMIENTO DE LAS LOCACIONES EN LA LISTA DE LISTAS DE
				 * RELACIONES DE LOCACIÓN DE LARGO=MaxConsultGoogle
				 **/
				// listOfListRelationLocationsGoogle.add(countOfQueryGoogle,
				// listRelationLocationWithMaxLength);
				// listRelationLocationWithMaxLength=new ArrayList
				// <RelationLocation>();
				countOfLocation = 1;
				countOfQueryGoogle++;
				oldPositionStringList.clear();
				// destinyPosition = oldPositionStringList.toArray(new
				// String[oldPositionStringList.size()]);
				flag = false;

			} else {
				flag = true;
			}
			countOfLocation++;

		}

		/**
		 * EMPAQUETADO PARA CONSULTA A GOOGLE CON ARRAY LONG DE LARGO <
		 * MaxConsultGoogle
		 **/
		if (flag == true) {// && (newPositionStringList.size() ==
							// oldPositionStringList.size() )
			// System.out.println("INGRESO A EMPAQUETADO LARGO <
			// MaxConsultGoogle");
			
			listOfListLocationDestiny.add(countOfQueryGoogle, listOfDestinyLocation);
			
			/**
			 * AlMACENAMIENTO DEL RESTO DE LAS LOCACIONES EN LA LISTA DE LISTAS
			 * DE RELACIONES DE LOCACIÓN
			 **/
			// listOfListRelationLocationsGoogle.add(countOfQueryGoogle,
			// listRelationLocationWithMaxLength);

			// origenPosition = newPositionStringList.toArray(new
			// String[newPositionStringList.size()]);
			destinyPosition = oldPositionStringList.toArray(new String[oldPositionStringList.size()]);

			// idPositionLatLng.add(contDeConsultas, idOriginDestino);

			// origenLocationsList.add(countOfQueryGoogle, origenPosition);

			destinyLocationsList.add(countOfQueryGoogle, destinyPosition);

			// idLocationsList.add(contDeArreglos, idOriginDestino);

			// listContainerOfListOrigDest.add(countOfQueryGoogle,
			// idPositionLatLng);

		}
		/**
		 * Leyendo DATOS PARA GENERAR LA CONSLUTA DE GOOGLE BORRAR ESTO DESPUES
		 */
//		System.out.println("destinyLocationsList.size()" + destinyLocationsList.size());
//		for (int i = 0; i < destinyLocationsList.size(); i++) {
//			System.out.println("destinyLocationsList.get(i).length" + destinyLocationsList.get(i).length);
//			for (int j = 0; j < destinyLocationsList.get(i).length; j++) {
//				System.out.println("destinyLocationsList.get(i)" + destinyLocationsList.get(i)[j]);
//			}
//		}

		System.out.println(":::::::::PREGUNTANDO A GOOGLE:::::::::");

		/**** CONSULTANDO A GOOGLE ****/
			int contadorDeConsultas = 0;
//			System.out.println(":::::::::en try:::::::::" + countOfQueryGoogle);
			// PARA CADA QUERYGOOGLE DE TAMAÑO MAXIMO DE 25
			if(listOfListLocationOrigins.size() == origenLocationsList.size()){//listOfListLocationDestiny
				System.out.println("Los arreglos listOfListLocationOrigins(para R.L.) y origenLocationsList(Google) tiene el mismo tamaño (OK) ");
			
				for (int count1 = 0; count1 < origenLocationsList.size(); count1++) {
				
				// for(int cont2=0; cont2 <
				// destinyLocationsList.get(count1).length; cont2++){
				// System.out.println(":::::"+cont2+")
				// destino"+destinyLocationsList.get(count1)[cont2]);
				// }

				// System.out.println("::::Contador:"+count1);
				// System.out.println("::::Tamaño
				// origenLocationsList.get(contOldLoc).length:"+origenLocationsList.get(count1).length);
				// System.out.println(":::Tamaño
				// destinyLocationsList.get(contOldLoc).length:"+destinyLocationsList.get(count1).length);
				// System.out.println(":::Tamaño
				// listOfListRelationLocationsGoogle.get(count1).size():"+listOfListRelationLocationsGoogle.get(count1).size());
				
				
//				System.out.println(" estinyLocationsList.size() :" + destinyLocationsList.size());
				for (int cont2 = 0; cont2 < destinyLocationsList.size(); cont2++) {
//					System.out.println("COUNT1 :" + count1 + " COUNT2:" + cont2);
//					System.out.println("LISTO PARA CONSULTAR origenLocationsList.get(count1)"
//							+ origenLocationsList.get(count1).length);
//					System.out.println("LISTO PARA CONSULTAR destinyLocationsList.get(count1) "
//							+ destinyLocationsList.get(cont2).length);
//					for (int contOrigin = 0; contOrigin < origenLocationsList.get(count1).length; contOrigin++) {
//						System.out.println("contOrgin: " + contOrigin + " Location Origins :"
//								+ origenLocationsList.get(count1)[contOrigin]);
//
//					}
//					for (int contDest = 0; contDest < destinyLocationsList.get(cont2).length; contDest++) {
//						System.out.println("contDest: " + contDest + " Location Destiny :"
//								+ destinyLocationsList.get(cont2)[contDest]);
//					}

					System.out.println("consultando bloque " + count1 + "," + cont2);
					
					try {
						DistanceMatrix result = DistanceMatrixApi.getDistanceMatrix(context,origenLocationsList.get(count1), destinyLocationsList.get(cont2)).await();
						
						for(int originCount=0; originCount < listOfListLocationOrigins.get(count1).size(); originCount++){
							for(int destinCount=0; destinCount <  listOfListLocationDestiny.get(cont2).size(); destinCount++){
								RelationLocation relationLocation = new RelationLocation();
								relationLocation.setIdFirstLocation(listOfListLocationOrigins.get(count1).get(originCount).getLocationId());
								relationLocation.setIdSecondLocation(listOfListLocationDestiny.get(cont2).get(destinCount).getLocationId());
								relationLocation.setGoingDistance((double) result.rows[originCount].elements[destinCount].distance.inMeters);
								relationLocation.setGoingDuration((double) result.rows[originCount].elements[destinCount].duration.inSeconds);
								//relationLocation.setFirstLocation();
								
//								relationLocation.setIdFirstLocation(idFirstLocation);
//								 relationLocation.setSecondLocation(destinyPositionLatLng);
//								 relationLocation.setIdSecondLocation(idPositionDestiny);
								listRelationLocationDeRetorno.add(relationLocation);
								
							}
							
						}
							
						
						
						//listRelationLocationDeRetorno
						contadorDeConsultas++;
						resultsList.add(result);
						

							
					} catch (Exception e) {
						cont2--;
						//e.printStackTrace();
						System.out.println("error!!!");
							
						
						try {
							Thread.sleep(2000);
							
							
						} catch (Exception e2) {
							// TODO: handle exception
						}
						
						
					}					
//					if (contadorDeConsultas % 20 == 0) {
//						Thread.sleep(3000);
//
//					}
//					System.out.println("CONSULTA REALIZADA" + contadorDeConsultas);

				}
			}
		}else{
			System.out.println("Sistema Caido.Los arreglos listOfListLocationOrigins(para R.L.) y origenLocationsList(Google) NO tiene el mismo tamaño (FAIL) ");
		}

//			for (int x = 0; x < resultsList.size(); x++) {
//				for (int z = 0; z < resultsList.get(x).rows.length; z++) {
//					for (int y = 0; y < resultsList.get(x).rows[z].elements.length; y++) {
//						System.out.println(y + "-y) Elemento: " + resultsList.get(x).rows[z].elements[y].distance);
//					}
//				}
//
//			}
		
		Date dd = new Date();

		/** COMPLETANDO EL RelationLocation con los DATOS DE GOOGLE **/

		/** lista de hasta largo 25 con las locaciones **/
	//---------------------------FIN-----------------------//

	

		// System.out.println("Entrega de datos finales");
		
		
		Date f = new Date();
		System.out.println("****************************Tiempo Ahora que terminé:" + f.toString());
		logger.info("hora: "+ f.toString());


		return listRelationLocationDeRetorno;// arrayDistanceMatrixResult;
	}

	@Override
	public void getTimeByGoogle() {
		// TODO Auto-generated method stub
		// return null;
	}

}
