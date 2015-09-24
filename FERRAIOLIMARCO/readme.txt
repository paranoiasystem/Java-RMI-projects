Modifiche effettuate:

IServer.java

+ 	cambiato valore di ritorno del metodo iscrivo

Server.java

+ 	modificato il metodo iscrivo per permettere all'utente nel caso l'username sia occupato, 
	di inserirne uno alternativo.
+	aggiunto l'abbandono sicuro
+	aggiunta funzione notificaUtente per ottimizzare il riuso del codice
+	Varie correzioni logiche degli if

IClient.java

+	aggiunto il metodo scollegati per implementare l'abbandono sicuro

Client.java

+	aggiunto controllo per verificare se l'username è occupato
+	aggiunto controllo sui comandi non validi
+ 	aggiunto parsing dei parametri
+	Varie correzioni logiche degli if
+   Aggiunto comando !help