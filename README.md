# ER Simulator

**PREZENTAREA CLASELOR SI A METODELOR**
Proiectul pe care l-am realizat, este compus, pe langa cele clasele din schelet,
din inca 10 clase pe care mi le-am definit in vederea implementarii simulatorului.
Aceste clase sunt:

***1. Main --> Cea mai importanta clasa din program***

* Aici se realizeaza citirea dinfisier si desfasurarea simularii.
* Dupa citirea din fisier, doctorii sunt salvati intr-o lista auxiliara 
folosita de ERObserver pentru afisarea rezultatelor simularii in fiecare runda;
* Pentru fiecare runda, patientii care sosesc in spital sunt adaugati in Triage Queue;
* In Triage Queue, asistentele vor consulta pacientii si le vor determina gradul de urgenta;
* Simulam indisponibilitatea asistentelor spre consultare prin setarea
unei variabile booleene isAvailable la false cand aceasta consulta
un pacient si resetarea la true atunci cand a terminat de consultat
pacientul (devine disponibila pentru urmatoarea runda)
* Pacientii cu gradul de urgenta determinat sunt trimisi in Examinations
Queue unde vor astepta sa fie vazuti de doctori;
* Fiecare pacient va fi consultat de un doctor;
* Cand un doctor consulta/opereaza un pacient, doctorul este scos din
lista de doctori si adaugat la finalul acesteia (simularea
indisponibilitatii doctorilor);
* Pentru fiecare pacient din lista de examinare, se parcurge lista de
doctori si se cauta primul doctor care poate trata afectiunea
pacientului;
* Daca pacientul trebuie operat, se verifica in plus daca doctorul
este chirurg;
* Daca pacientul trebuie operat si nu a fost gasit niciun doctor care
sa il poata opera, pacientul este transferat la alt spital;
* Daca pacientul a fost operat si/sau trebuie spitalizat, se va cauta
in lista de asistente o asistenta care sa ii prescrie regulat
tratamentul;
* Daca toate asistentele ingrijesc pacienti, pacientul va fi atribuit
primei asistente din lista de asistente;
* Daca doctorul a trimis pacientul la ER Technician, acesta va fi
adaugat in Investigations Queue;
* Pacientul va fi scos din Examinations Queue;
* Doctorul care a consultat/operat pacientul va fi adaugat la finalul
listei de doctori;
* Pentru fiecare pacient din coada de investigatii, se va cauta un
ER Technician care sa il consulte si sa ii dea rezultatul unor
analize;
* Analog ca in Triage Queue, investigatorii devin indisponibili pentru
runda curenta in momentul in care consulta un pacient si vor redeveni
disponibili pentru runda urmatoare;
* La finalul rundei, fiecare asistenta va administra tratamentul
pacientilor pe care ii ingrijeste si se va notifica Observer-ul de
schimbarile din runda curenta.


***2. Doctor --> clasa in care sunt retinute datele doctorilor si metodele lor***

* *canCure* - decide daca doctorul poate trata sau nu o anumita afectiune;
* *cure* - doctorul consulta pacientul;
	* daca este vazut pentru prima data si i se poate prescrie
	  un tratament este trimis acasa; altfel, este trimis la
	  investigatii;
	* daca a fost la investigatii si trebuie spitalizat, i se
	  va actualiza statusul si va fi spitalizat;
	* daca a fost la investigatii si trebuie trimis acasa, i se
	  va prescrie un tratament si va fi trimis acasa;
* *consult* - doctorul verifica daca unul din pacientii internati
de el si-a terminat tratamentul sau daca i-as scazut
severitatea la 0 si poate fi trimis acasa sau nu;
* *operate* - doctorul opereaza pacientul, ii calculeaza noua
severitate si il spitalizeaza;
* *hospitalize* - doctorul ii calculeaza pacientului numarul de runde
de spitalizare si il adauga in lista de pacienti spitalizati de el.


***3. ERTechnician --> clasa in care sunt retinute datele investigatorilor si metodele***

* *examinate* - investigatorul ii face pacientului analize
si decide daca acesta poate fi trimis acasa,
trebuie spitalizat sau trebuie operat.


***4. Nurse --> clasa in care sunt retinute datele asistentelor si metodele lor***

* calculateUrgency - calculeaza gradul de urgenta al unui pacient din Triage Queue;
* treatPatients - administreaza tratamentul tuturor pacientilor
pe care ii are in grija si le calculeaza numarul de runde de spitalizare ramase;


***5. Patient --> clasa in care sunt retinute datele pacientilor***


***6. ERObserver --> Observer-ul folosit pentru afisarea rezultatului simularii in fiecare runda***

* Pentru toti pacientii care au ajuns in spital pana in runda curenta se afiseaza starea lor;
* Pacientii sunt sortati in ordine alfabetica;
* Se afiseaza pacientii care sunt ingrijiti de asistente in ordine alfabetica si numarul de runde de spitalizare ramase;
* Pentru fiecare doctor (in ordinea in care sunt in input), se afiseaza verdictul oferit pacientilor spitalizati de acestia 
sortati alfabetic dupa nume;
* Daca doctorul a decis ca un pacient poate pleca acasa, acesta este scos din ingrijirea asistentei si din lista de pacienti a
doctorului.


***7. EmergencyRoom --> Clasa subiect pentru observator***

* Contine toate liste de doctori, pacienti, asistente si investigatori;
* Contine Triage, Examinations si Investigations Queue.


***8. NameComparator --> Clasa comparator pentru sortarea pacientilor alfabetic***

* Folosita de ERObserver pentru sortarea pacientilor pentru afisare.


***9. PatientComparator --> Clasa comparator pentru sortarea pacientilor dupa urgenta, severitate si nume***

* Folosita pentru sortarea a Examinations Queue si Investigations Queue.


***10. SeverityComparator --> Clasa comparator pentru sortarea pacientilor dupa severitate si nume***

* Folosita pentru sortarea a Triage Queue.
