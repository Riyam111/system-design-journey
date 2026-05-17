# 🎯 Strategy Design Pattern — Revision Notes

> **One-liner:** Define a family of algorithms, put each in its own class, and make them swappable at runtime.

---

## 🧠 Core Idea

- Separate **"what varies"** (the algorithm) from **"what stays the same"** (the context that uses it)
- Instead of one big class with `if-else` for every variation → each variation gets its **own class**
- The main class just **delegates** work to whichever strategy is plugged in

---

## ❌ Problem Without Strategy Pattern

```java
// BAD: One class doing everything with messy if-else
class ShippingCalculator {
    double calculate(Order order, String method) {
        if (method.equals("flat"))        return 5.0;
        else if (method.equals("weight")) return order.weight * 1.5;
        else if (method.equals("express")) return 20.0;
        // ... keeps growing forever
    }
}
```

**What goes wrong:**
- Every new shipping method → modify this class (breaks OCP)
- Hard to test one method in isolation
- Class grows huge and messy
- Duplicate logic if reused elsewhere

---

## ✅ Solution: Strategy Pattern

### 3 Key Players

| Role | What it does | Example |
|---|---|---|
| **Strategy Interface** | Common contract all algorithms follow | `ShippingStrategy` |
| **Concrete Strategies** | Each algorithm in its own class | `FlatRateShipping`, `WeightBasedShipping` |
| **Context** | Holds a strategy reference, delegates to it | `ShippingCostService` |

---

## 🏗️ Structure

```
«interface»
ShippingStrategy
  + calculateCost(order): double
        ▲
        |
   _____|_____________________________
   |           |           |         |
FlatRate   WeightBased  Distance  Express
Shipping    Shipping    Shipping  Shipping

                    uses
ShippingCostService ────────► ShippingStrategy
```

---

## 💻 Code (Java)

### Step 1 — Strategy Interface
```java
interface ShippingStrategy {
    double calculateCost(Order order);
}
```

### Step 2 — Concrete Strategies
```java
class FlatRateShipping implements ShippingStrategy {
    public double calculateCost(Order order) {
        return 5.0; // fixed fee
    }
}

class WeightBasedShipping implements ShippingStrategy {
    public double calculateCost(Order order) {
        return order.getWeight() * 1.5;
    }
}

class ExpressShipping implements ShippingStrategy {
    public double calculateCost(Order order) {
        return 20.0 + order.getWeight() * 2.0;
    }
}
```

### Step 3 — Context Class
```java
class ShippingCostService {
    private ShippingStrategy strategy;

    // inject via constructor
    public ShippingCostService(ShippingStrategy strategy) {
        this.strategy = strategy;
    }

    // or swap at runtime
    public void setStrategy(ShippingStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculate(Order order) {
        return strategy.calculateCost(order); // just delegates
    }
}
```

### Step 4 — Client Usage
```java
ShippingCostService service = new ShippingCostService(new FlatRateShipping());
service.calculate(order); // → 5.0

// upgrade to express at runtime — no code change in ShippingCostService!
service.setStrategy(new ExpressShipping());
service.calculate(order); // → express price
```

---

## 🌍 Real-World Analogy

**Going to the airport:** you can drive, take a taxi, or use the metro.  
Each is a **strategy**. You (the traveler/context) just pick one and go.  
The **destination** doesn't change — only **how you get there** does.

---

## 📦 Another Example — Payment Processing

```java
interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) { /* charge card */ }
}

class PayPalPayment implements PaymentStrategy {
    public void pay(double amount) { /* send paypal request */ }
}

class CryptoPayment implements PaymentStrategy {
    public void pay(double amount) { /* initiate crypto transfer */ }
}

// CheckoutService just calls strategy.pay() — doesn't care which one
```

Adding Apple Pay tomorrow? Just create `ApplePayPayment` — **nothing else changes**.

---

## ✅ Benefits

| Benefit | Why it matters |
|---|---|
| **Open/Closed Principle** | Add new strategy = new class only, zero edits to existing code |
| **Single Responsibility** | Each class does one thing |
| **Testable** | Test each strategy in isolation, no complex setup needed |
| **Runtime flexibility** | Swap strategies on the fly |
| **Type-safe** | No fragile string-based dispatch, compiler catches bugs |
| **Composition > Inheritance** | Loose coupling between context and algorithms |

---

## ⚠️ When NOT to Use

- Only 2 algorithms and they'll never change → simple `if-else` is fine
- Algorithms don't share a common interface → forced abstraction gets messy
- Overkill for very simple, rarely-changing logic

---

## 🔑 Quick Recall Checklist

- [ ] Is there a **common interface** all strategies implement?
- [ ] Does each algorithm live in its **own class**?
- [ ] Does the **context delegate** (not decide) which algorithm runs?
- [ ] Can you **swap strategies at runtime** without touching context?
- [ ] Adding a new strategy requires **zero changes** to existing code?

If all ✅ → you've got Strategy Pattern right.

---

## 🔗 Pattern Type & Related Patterns

**Type:** Behavioral — deals with how objects communicate and delegate responsibility.

| Pattern | Similarity | Difference |
|---|---|---|
| **Template Method** | Same goal (vary algorithm) | Uses inheritance, not composition |
| **Command** | Also encapsulates behavior | Encapsulates actions/requests, not algorithms |
| **State** | Also swaps behavior at runtime | Object changes its own behavior based on internal state |

### Detailed  blog 
https://algomaster.io/learn/lld/strategy