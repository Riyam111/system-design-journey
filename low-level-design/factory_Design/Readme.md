# Factory Method Design Pattern

# What is Factory Method Pattern?

Factory Method Pattern is a **Creational Design Pattern** used to create objects without exposing the object creation logic to the client.

Instead of creating objects directly using `new`, the client asks a **Factory/Creator** class to create and return the required object.

---

# Simple Definition (Interview Friendly)

> Factory Method Pattern defines an interface or abstract method for creating objects, but subclasses decide which concrete object to create.

---

# Very Simple Explanation

Instead of doing this:

```java
Notification n = new EmailNotification();
```

we do this:

```java
NotificationCreator creator =
        new EmailNotificationCreator();

creator.send("Hello");
```

Here:

- Client does not know how object is created
- Creator class handles object creation
- Subclasses decide which object to create

---

# Real World Analogy

Think of a food delivery app.

You order:

```text
Pizza
```

You do not cook it yourself.

The app decides:

- Which restaurant
- Which chef
- How food is prepared

You only receive the final product.

Similarly in Factory Method:

- Client asks creator for object
- Creator decides which object to create
- Client uses object without knowing creation details

---

# Main Components of Factory Method Pattern

| Component | Role |
|---|---|
| Product | Common interface/base class |
| Concrete Product | Actual implementation classes |
| Creator | Abstract class containing factory method |
| Concrete Creator | Subclasses deciding object creation |
| Client | Uses creator to work with objects |

---

# Example Used

We created a Notification System with:

- Email Notification
- SMS Notification
- Push Notification
- Slack Notification

---

# Step-by-Step Structure

---

# 1. Product Interface

```java
interface Notification {
    void send(String message);
}
```

## Purpose

- Defines common behavior
- All notifications must implement `send()`

---

# 2. Concrete Products

```java
class EmailNotification implements Notification {
    public void send(String message) {
        System.out.println("Sending email: " + message);
    }
}
```

Other concrete products:

- `SMSNotification`
- `PushNotification`
- `SlackNotification`

## Purpose

- Actual objects used in program
- Each class provides its own implementation

---

# 3. Creator Class

```java
abstract class NotificationCreator {

    public abstract Notification createNotification();

    public void send(String message) {

        Notification notification =
                createNotification();

        notification.send(message);
    }
}
```

This is the most important class.

---

# Important Points

## Factory Method

```java
createNotification()
```

is called the **Factory Method**.

### Purpose

Subclasses decide which object to create.

---

## Common Workflow

```java
send()
```

contains shared business logic.

### Workflow

```text
1. Create object
2. Use object
```

Only object type changes.

---

# 4. Concrete Creators

Example:

```java
class EmailNotificationCreator
        extends NotificationCreator {

    @Override
    public Notification createNotification() {
        return new EmailNotification();
    }
}
```

## Purpose

Decides which concrete object to create.

Other creators:

- `SMSNotificationCreator`
- `PushNotificationCreator`
- `SlackNotificationCreator`

---

# Execution Flow (Most Important)

# Main Method

```java
NotificationCreator creator;

creator = new EmailNotificationCreator();

creator.send("Welcome");
```

---

# Detailed Flow

## Step 1

```java
creator = new EmailNotificationCreator();
```

Creates:

```text
EmailNotificationCreator object
```

NOT `EmailNotification` object.

---

## Step 2

```java
creator.send("Welcome");
```

Calls `send()` method from parent class.

Control goes to:

```java
NotificationCreator.send()
```

---

## Step 3

Inside `send()`:

```java
Notification notification =
        createNotification();
```

Runtime polymorphism happens.

Since actual object is:

```text
EmailNotificationCreator
```

Java calls:

```java
EmailNotificationCreator.createNotification()
```

---

## Step 4

This executes:

```java
return new EmailNotification();
```

Now actual `EmailNotification` object is created.

---

## Step 5

```java
notification.send(message);
```

Calls:

```java
EmailNotification.send()
```

Final output:

```text
Sending email: Welcome
```

---

# Important Concept

Factory Method Pattern separates responsibilities:

| Responsibility | Handled By |
|---|---|
| Object Usage | Parent Creator |
| Object Creation | Child Creator |

---

# Key Advantages

# 1. Loose Coupling

Client does not depend on concrete classes.

Client only works with interface/base class.

---

# 2. Open/Closed Principle

New notification types can be added without changing existing code.

Example:

```text
WhatsAppNotification
```

can be added easily.

---

# 3. Reusability

Common workflow stays in parent class.

No duplicate code.

---

# 4. Better Maintainability

Object creation logic is centralized and easier to manage.

---

# Why Not Create Objects Directly?

Without Factory Method:

```java
if(type.equals("EMAIL")){
    notification = new EmailNotification();
}
else if(type.equals("SMS")){
    notification = new SMSNotification();
}
```

## Problems

- Large if-else chains
- Tight coupling
- Hard to extend
- Main/client knows too much

Factory Method solves these problems.

---

# Key Interview Point

## Parent Class Responsibility

```text
Defines workflow
```

## Child Class Responsibility

```text
Decides object creation
```

---

# Memory Trick

```text
Parent = HOW to use object

Child = WHICH object to create
```

---

# When to Use Factory Method Pattern?

Use when:

- Multiple object types exist
- Exact object type decided at runtime
- Object creation logic should be hidden
- Code should follow Open/Closed Principle
- Common workflow exists but object varies

---

# Difference Between Simple Factory and Factory Method

| Simple Factory | Factory Method |
|---|---|
| One factory class | Multiple creator subclasses |
| Uses if-else/switch | Uses inheritance |
| Centralized creation | Subclasses decide creation |
| Less flexible | More extensible |

---

# Common Interview Questions

# Q1. What problem does Factory Method solve?

It removes tight coupling between client and concrete classes by delegating object creation to creator subclasses.

---

# Q2. What is Factory Method?

The method responsible for object creation.

Example:

```java
createNotification()
```

---

# Q3. Which principles are followed?

- Open/Closed Principle
- Dependency Inversion Principle
- Program to Interface

---

# Final Summary

Factory Method Pattern is used to:

- Encapsulate object creation
- Separate creation logic from business logic
- Allow subclasses to decide object type
- Make code flexible and extensible

Core idea:

```text
Client → Creator → Factory Method → Concrete Object
```

---

# One-Line Interview Explanation

> Factory Method Pattern defines a method for creating objects, but lets subclasses decide which concrete object to instantiate. It helps achieve loose coupling and extensibility.

## source of documentation
https://algomaster.io/learn/lld/factory-method