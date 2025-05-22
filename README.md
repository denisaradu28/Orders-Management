# 🧾 Orders Management Application

Această aplicație Java este un sistem desktop de gestionare a comenzilor, clienților și produselor, cu interfață grafică bazată pe Swing și persistare în baza de date MySQL.

## 🔧 Tehnologii folosite

- Java SE 17+
- JDBC (Java Database Connectivity)
- Swing (Java GUI)
- MySQL
- DAO Pattern & MVC arhitectură

---

## 📁 Structura proiectului

├── BusinessLogicLayer/
│ ├── ClientBLL.java
│ ├── OrderBLL.java
│ ├── OrderItemBLL.java
│ └── ProductBLL.java
├── DataAccessLayer/
│ ├── AbstractDAO.java
│ ├── ClientDAO.java
│ ├── OrdersDAO.java
│ ├── OrderItemDAO.java
│ ├── ProductDAO.java
│ └── LogDAO.java
├── Model/
│ ├── Client.java
│ ├── Product.java
│ ├── Orders.java
│ ├── OrderItem.java
│ ├── OrderItemView.java
│ └── Bill.java
├── Presentation/
│ ├── View.java
│ ├── Controller.java
│ ├── Main.java
│ ├── ClientPanel.java
│ ├── ProductPanel.java
│ ├── OrderPanel.java
│ ├── LogPanel.java
│ └── TableGenerator.java
└── Connection/
└── ConnectionFactory.java


---

## ✅ Funcționalități principale

- **Client Management**: Adăugare, editare, ștergere și vizualizare clienți.
- **Product Management**: Gestionare produse (nume, preț, stoc).
- **Order Management**: Comenzi pentru clienți, adăugare produse în coș, facturare.
- **Logs**: Istoric comenzi/facturi.
- **Validări**: Toate operațiile sunt validate la nivelul BLL.

---

## 🛠️ Configurare și rulare

### 1. Configurare bază de date

Creează o bază de date MySQL numită `tp` și configurează în `ConnectionFactory.java`:

```java
private static final String DBURL = "jdbc:mysql://localhost:3306/tp";
private static final String USER = "root";
private static final String PASS = "parola_ta";
