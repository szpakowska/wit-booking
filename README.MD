# Booking

Prosty projekt ilustrujący elementy JDK i Hiberanete

## Wymagania

Projekt ma realizować następujące funkcje:
BOK-1 zarządzanie katalogiem produktów
BOK-1A - produkt moze być dodawany, kasowany, modyfikowany, przeglądany (CRUD)
BOK-1B - produkt powinien mieć nazwę opis, cenę, kategorie oraz inne atrybuty, które mogą zostać wykorzystane do
przeszukiwania katalogu produktów
BOK-2 zarządzanie terminarzem w ramach którego zapisane są zaplanowane w czasie sesje dla wybranych pozycji z katalogu
BOK-2A - sesja może być dodawana, kasowana, modyfikowana, przeglądana (CRUD)
BOK-2B - nie można wprowadzać sesji zaległych (w przesłości)
BOK-3 rezerowawać dostępne sesje
BOK-3A - może być tylko 1 rezerwacja dla sesji, która może zostać usunięta jedynie przez administratora systemu
BOK-3B - nie można rezerwować sesji w przeszłości oraz sesji nie znajdujących się w terminarzu
BOK-3C - można rezerwować całą serie sesji
BOK-3D - należy pozwolić wyszukać wolną sesję wg nazwy (również jej fragmentu), opisu (jego fragmentu),
terminu (dopuszczalny zakre od - do), ceny (od - do), kategorii, wybranych innych atrybutów

### Uzgodnienia

#### Baza danych

- nazwa bazy danych/schematu
- nazwa użytkownika
- hasło

#### GitHub

- jakie branch'e wykorzystujemy: feature -> PR -> master
- jaki wzorzec nazw branche'y stosujemy: feature/BOK1A
- czy włączamy automatycznie budowanie

#### Definition of Ready (DoR), Definition of Done (DoD)

Z uwagi na fakt, że skupiamy się na programowaniu a nie analizie, pomijamy DoR

##### DoD

- kod musi się budować
- zgłaszamy PR po zakończeniu prac nad całym zagadnieniem uzgodnionym jako zakres
- logika musi być pokryta min. w min 50% przez testy - jakie (po demo)
- musi być approval do PR od innego niż autor programisty

### Baza danych

    CREATE DATABASE booking;
    CREATE USER 'booking' identified by 'passwd';
    GRANT ALL ON booking.* to booking;
    FLUSH PRIVILEGES;

Przełączyć się na nowego użytkownika i sprawdzić czy na pewno może tworzyć obiekty

    create table test
    (
    id int null
    );
    DROP TABLE test;

URL do wykorzystania w programie

    jdbc:mysql://localhost:3306/booking