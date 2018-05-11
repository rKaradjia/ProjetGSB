#!/usr/bin/python
# -*- coding: utf-8 -*-

from flask import *
import json
import mysql.connector

# Créer l'objet Application Flask (Serveur)
app = Flask( __name__ )	

# Obtenir une connexion au SGBDR
connexionBD = mysql.connector.connect(
			host = 'localhost' ,
			user = 'root' ,
			password = 'azerty' ,
			database = 'GsbCRSlamV2015'
			#database = 'foody'
		)

# Page d'accueil
@app.route( '/' )
def accueil() :
	# Retourne un simple message (texte brut)
	return "GSB Services web"




#Se connecter
@app.route( '/login/<idVisiteur>/<mdpVisiteur>' , methods=['GET'] )
def getConnexion( idVisiteur , mdpVisiteur ) :
	#Tester ok
	#obtenir un cursor
	curseur = connexionBD.cursor()
	#Exécuter la requete
	curseur.execute( 'select VIS_MATRICULE,VIS_NOM,VIS_PRENOM,VIS_MDP from VISITEUR where VIS_MATRICULE = \'' + idVisiteur + '\' and VIS_MDP = \'' + mdpVisiteur + '\'')
	#curseur.execute( 'select VIS_MATRICULE,VIS_MDP from VISITEUR where VIS_MATRICULE = ' + idVisiteur +'')
	tuples = curseur.fetchall()
	curseur.close()
	visiteurs = []
	for unTuple in tuples :
		unVisiteur = { 'VIS_MATRICULE': unTuple[0] , 'VIS_NOM': unTuple[1] ,  'VIS_PRENOM': unTuple[2] , 'VIS_MDP': unTuple[3] }
		visiteurs.append( unVisiteur )
	reponse = json.dumps( visiteurs )
	print "Visiteur : " + reponse
	return Response( reponse, status=200 , mimetype='application/json' )
	
	
	
	
#Liste des RV en rapport avec un Visiteur,un Mois, une Annee
@app.route( '/rv/<idVisiteur>/<moisRV>/<anneeRV>' , methods=['GET'] )
def getRapportVisite( idVisiteur, moisRV, anneeRV ) :
	
	curseur = connexionBD.cursor()
	curseur.execute( 'select VISITEUR.VIS_MATRICULE,VIS_NOM,MONTH(RAP_DATE), YEAR(RAP_DATE), DAY(RAP_DATE), RAP_NUM,RAP_BILAN, RAP_CONF from VISITEUR inner join RAPPORT_VISITE on VISITEUR.VIS_MATRICULE = RAPPORT_VISITE.VIS_MATRICULE where VISITEUR.VIS_MATRICULE = \'' + idVisiteur + '\' and MONTH(RAP_DATE) = '+ moisRV +' and YEAR(RAP_DATE) = ' + anneeRV + '')
	tuples = curseur.fetchall()
	curseur.close()
	rapportVisites = []
	for unTuple in tuples :
		unRapportVisite = { 'VISITEUR': unTuple[0] , 'VIS_NOM': unTuple[1] , 'MONTH(RAP_DATE)': unTuple[2] , 'YEAR(RAP_DATE)': unTuple[3], 'DAY(RAP_DATE)': unTuple[4], 'RAP_NUM': unTuple[5],'RAP_BILAN': unTuple[6], 'RAP_CONF': unTuple[7] }
		rapportVisites.append( unRapportVisite )
	reponse = json.dumps( rapportVisites )
	print "RapportVisite : " + reponse
	return Response( reponse, status=200 , mimetype='application/json' )

	
#Liste des echantillons par rapport à un rapport (identifiant du rapport de visite)
@app.route( '/rv/echantillons/<idRapport>' , methods=['GET'] )
def getEchantillons( idRapport ) :
	
	curseur = connexionBD.cursor()
	#curseur.execute( 'select VISITEUR.VIS_MATRICULE,VIS_NOM,MONTH(RAP_DATE), YEAR(RAP_DATE), DAY(RAP_DATE), RAP_NUM,RAP_BILAN, RAP_CONF from VISITEUR inner join RAPPORT_VISITE on VISITEUR.VIS_MATRICULE = RAPPORT_VISITE.VIS_MATRICULE where VISITEUR.VIS_MATRICULE = \'' + idVisiteur + '\' and MONTH(RAP_DATE) = '+ moisRV +' and YEAR(RAP_DATE) = ' + anneeRV + '')
	curseur.execute('select MED_NOMCOMMERCIAL from OFFRIR inner join MEDICAMENT WHERE OFFRIR.RAP_NUM='+ idRapport + '');
	tuples = curseur.fetchall()
	curseur.close()
	desEchantillons = []
	for unTuple in tuples :
		unEchantillon = { 'MED_NOMCOMMERCIAL': unTuple[0] }
		desEchantillons.append( unEchantillon )
	reponse = json.dumps( desEchantillons )
	print "Liste des echantillons : " + reponse
	return Response( reponse, status=200 , mimetype='application/json' )





# Programme principal
if __name__ == "__main__" :
	# Démarrer le serveur
	app.run( debug = True , host = '0.0.0.0' , port = 5000 )
