# Shopping-HDV: Circuit Breaker & Reliability Patterns

A demonstration project showcasing the implementation of Circuit Breaker patterns and reliability engineering principles in a distributed shopping application.

## 📋 Table of Contents

- [Overview](#overview)
- [Circuit Breaker Pattern](#circuit-breaker-pattern)
- [Reliability Principles](#reliability-principles)
- [Architecture](#architecture)
- [Features](#features)
- [Getting Started](#getting-started)
- [Usage Examples](#usage-examples)
- [Configuration](#configuration)
- [Best Practices](#best-practices)
- [Monitoring](#monitoring)
- [Contributing](#contributing)

## 🎯 Overview

This project demonstrates how to build a resilient shopping application using the Circuit Breaker pattern and modern reliability engineering practices. It's designed to handle failures gracefully, prevent cascading failures, and maintain system stability even when downstream services are experiencing issues.

### Why Circuit Breaker?

In distributed systems, services often depend on other services. When a dependency fails, it can cause:
- **Cascading failures** throughout the system
- **Resource exhaustion** from retrying failed operations
- **Poor user experience** with long timeouts
- **System instability** and degraded performance

The Circuit Breaker pattern solves these problems by:
- Detecting failures quickly
- Preventing repeated attempts to failing services
- Allowing the system to recover gracefully
- Providing fallback mechanisms

## ⚡ Circuit Breaker Pattern

### What is a Circuit Breaker?

A Circuit Breaker acts like an electrical circuit breaker in your home. When it detects too many failures, it "trips" and stops sending requests to the failing service, giving it time to recover.

### Circuit States

```
┌─────────────┐
│   CLOSED    │ ◄── Normal operation, requests pass through
└──────┬──────┘
       │ Failure threshold exceeded
       ▼
┌─────────────┐
│    OPEN     │ ◄── Requests fail fast, no calls to service
└──────┬──────┘
       │ Timeout period elapsed
       ▼
┌─────────────┐
│ HALF-OPEN   │ ◄── Test if service recovered
└──────┬──────┘
       │
       └──► Back to CLOSED or OPEN based on results
```

### States Explained

1. **CLOSED (Normal Operation)**
   - All requests pass through to the service
   - Failures are counted
   - If failure rate exceeds threshold → transition to OPEN

2. **OPEN (Circuit Tripped)**
   - Requests fail immediately without calling the service
   - Returns cached data or default fallback
   - After timeout period → transition to HALF-OPEN

3. **HALF-OPEN (Testing Recovery)**
   - Limited number of test requests allowed through
   - If requests succeed → transition to CLOSED
   - If requests fail → transition back to OPEN

### Key Parameters

- **Failure Threshold**: Number/percentage of failures before opening circuit (e.g., 50%)
- **Timeout Period**: How long circuit stays open before testing recovery (e.g., 60 seconds)
- **Success Threshold**: Number of successful requests needed to close circuit (e.g., 3)
- **Request Volume Threshold**: Minimum requests before evaluating circuit state (e.g., 20)

## 🛡️ Reliability Principles

### 1. Fail Fast

Don't wait for timeouts. Detect failures quickly and respond immediately.

```javascript
// Bad: Long timeout causes cascading delays
await fetch(url, { timeout: 30000 });

// Good: Fail fast with circuit breaker
if (circuitBreaker.isOpen()) {
  return fallbackResponse();
}
```

### 2. Fallback Mechanisms

Always have a Plan B when services fail.

**Strategies:**
- Return cached data
- Provide default values
- Degrade functionality gracefully
- Show user-friendly error messages

### 3. Timeout Management

Set appropriate timeouts at every integration point.

```javascript
// Service-specific timeouts
const TIMEOUT_CONFIG = {
  productService: 2000,    // Fast operations
  paymentService: 5000,    // Critical but slower
  inventoryService: 3000   // Medium priority
};
```

### 4. Bulkhead Pattern

Isolate resources to prevent total system failure.

- Separate thread pools for different services
- Limit concurrent requests per service
- Prevent resource exhaustion

### 5. Retry with Exponential Backoff

Retry failed operations intelligently.

```javascript
// Exponential backoff: 1s, 2s, 4s, 8s
const delay = Math.min(1000 * (2 ** attempt), MAX_DELAY);
```

### 6. Health Checks

Continuously monitor service health.

- Active health checks (ping endpoints)
- Passive health checks (monitor request results)
- Graceful degradation based on health status

## 🏗️ Architecture

### System Components

```
┌─────────────────────────────────────────────────────────┐
│                    API Gateway                          │
│              (Load Balancer + Rate Limiter)            │
└────────────────────┬────────────────────────────────────┘
                     │
        ┌────────────┼────────────┐
        │            │            │
        ▼            ▼            ▼
┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│   Product    │ │   Payment    │ │  Inventory   │
│   Service    │ │   Service    │ │   Service    │
│ [Circuit     │ │ [Circuit     │ │ [Circuit     │
│  Breaker]    │ │  Breaker]    │ │  Breaker]    │
└──────────────┘ └──────────────┘ └──────────────┘
        │            │            │
        └────────────┼────────────┘
                     │
                     ▼
             ┌──────────────┐
             │   Database   │
             │  [Resilient  │
             │  Connection] │
             └──────────────┘
```

### Service Integration with Circuit Breakers

Each service integration includes:
- Circuit breaker for failure detection
- Fallback mechanism for degraded operation
- Metrics collection for monitoring
- Health check endpoints

## ✨ Features

### Implemented Patterns

- ✅ **Circuit Breaker**: Prevents cascading failures
- ✅ **Retry Logic**: Automatic retry with exponential backoff
- ✅ **Timeout Management**: Service-specific timeout configurations
- ✅ **Fallback Strategies**: Graceful degradation with cached data
- ✅ **Health Monitoring**: Real-time service health tracking
- ✅ **Metrics Collection**: Detailed performance and reliability metrics
- ✅ **Rate Limiting**: Prevent system overload
- ✅ **Bulkhead Isolation**: Resource isolation per service

### Shopping Application Features

- Product catalog with circuit-protected API calls
- Order processing with fallback mechanisms
- Payment integration with retry logic
- Inventory management with eventual consistency
- User authentication with cached sessions
- Search functionality with degraded results

## 🚀 Getting Started

### Prerequisites

- Node.js 16+ or Python 3.8+ or Java 11+
- Docker (optional, for containerized deployment)
- Redis (for distributed caching)
- Monitoring tools (Prometheus/Grafana recommended)

### Installation

```bash
# Clone the repository
git clone https://github.com/Geniedev1/shopping-HDV.git
cd shopping-HDV

# Install dependencies
npm install
# or
pip install -r requirements.txt
# or
mvn clean install

# Configure environment
cp .env.example .env
# Edit .env with your configuration

# Start the application
npm start
# or
python main.py
# or
java -jar target/shopping-hdv.jar
```

### Quick Start with Docker

```bash
docker-compose up -d
```

## 📖 Usage Examples

### Example 1: Product Service with Circuit Breaker

```javascript
const circuitBreaker = new CircuitBreaker({
  failureThreshold: 50,      // Open after 50% failures
  timeout: 60000,            // Stay open for 60 seconds
  successThreshold: 3,       // Close after 3 successes
  volumeThreshold: 10        // Minimum 10 requests
});

async function getProduct(productId) {
  return await circuitBreaker.execute(
    // Primary operation
    async () => {
      const response = await fetch(`/api/products/${productId}`, {
        timeout: 2000
      });
      return response.json();
    },
    // Fallback operation
    async () => {
      // Return cached product or default data
      return cache.get(`product:${productId}`) || {
        id: productId,
        name: 'Product temporarily unavailable',
        available: false
      };
    }
  );
}
```

### Example 2: Payment Processing with Retry

```javascript
async function processPayment(paymentData) {
  const retryPolicy = {
    maxAttempts: 3,
    backoff: 'exponential',
    initialDelay: 1000
  };

  return await retry(
    async () => {
      if (paymentCircuit.isOpen()) {
        throw new Error('Payment service unavailable');
      }
      return await paymentService.charge(paymentData);
    },
    retryPolicy,
    // Fallback: Queue for later processing
    async () => {
      await paymentQueue.add(paymentData);
      return { status: 'queued', message: 'Payment queued for processing' };
    }
  );
}
```

### Example 3: Monitoring Circuit Breaker State

```javascript
// Get circuit breaker metrics
app.get('/health/circuits', (req, res) => {
  const metrics = {
    productService: {
      state: productCircuit.getState(),
      failures: productCircuit.getFailureCount(),
      successes: productCircuit.getSuccessCount(),
      lastFailure: productCircuit.getLastFailureTime()
    },
    paymentService: {
      state: paymentCircuit.getState(),
      failures: paymentCircuit.getFailureCount(),
      successes: paymentCircuit.getSuccessCount(),
      lastFailure: paymentCircuit.getLastFailureTime()
    }
  };
  res.json(metrics);
});
```

## ⚙️ Configuration

### Circuit Breaker Configuration

```yaml
# config/circuit-breaker.yaml
circuit_breakers:
  product_service:
    failure_threshold: 50        # Percentage
    timeout: 60000              # Milliseconds
    success_threshold: 3        # Consecutive successes
    volume_threshold: 10        # Minimum requests
    
  payment_service:
    failure_threshold: 25       # More sensitive
    timeout: 120000            # Longer recovery time
    success_threshold: 5       # More successes needed
    volume_threshold: 5        # Lower threshold
    
  inventory_service:
    failure_threshold: 60
    timeout: 30000
    success_threshold: 2
    volume_threshold: 15
```

### Retry Configuration

```yaml
# config/retry.yaml
retry_policies:
  default:
    max_attempts: 3
    backoff_type: exponential
    initial_delay: 1000
    max_delay: 10000
    
  critical_operations:
    max_attempts: 5
    backoff_type: exponential
    initial_delay: 500
    max_delay: 30000
```

### Timeout Configuration

```yaml
# config/timeouts.yaml
timeouts:
  product_service: 2000
  payment_service: 5000
  inventory_service: 3000
  user_service: 1500
  search_service: 4000
```

## 🎯 Best Practices

### 1. Set Appropriate Thresholds

- **Don't be too aggressive**: Opening circuit too early can cause unnecessary failures
- **Don't be too lenient**: Waiting too long can cause cascading failures
- **Monitor and adjust**: Use metrics to fine-tune thresholds

### 2. Implement Meaningful Fallbacks

```javascript
// Good fallback strategies
const fallbackStrategies = {
  // Return cached data
  cached: () => cache.get(key),
  
  // Return default/safe values
  default: () => defaultValue,
  
  // Degrade functionality
  degraded: () => basicVersionOfFeature(),
  
  // Inform user
  userFriendly: () => ({ error: 'Feature temporarily unavailable' })
};
```

### 3. Monitor Everything

Track these metrics:
- Circuit breaker state changes
- Failure rates and types
- Response times
- Fallback usage
- Recovery times

### 4. Test Failure Scenarios

```javascript
// Chaos engineering - simulate failures
describe('Circuit Breaker Resilience', () => {
  it('should open circuit after threshold failures', async () => {
    // Simulate service failures
    for (let i = 0; i < 10; i++) {
      await expect(service.call()).rejects.toThrow();
    }
    expect(circuitBreaker.getState()).toBe('OPEN');
  });
  
  it('should use fallback when circuit is open', async () => {
    circuitBreaker.forceOpen();
    const result = await service.call();
    expect(result).toBe(fallbackValue);
  });
});
```

### 5. Document Recovery Procedures

- Automated recovery mechanisms
- Manual intervention procedures
- Escalation paths
- Post-incident review process

## 📊 Monitoring

### Key Metrics to Track

1. **Circuit Breaker Metrics**
   - State (CLOSED/OPEN/HALF-OPEN)
   - Failure rate
   - Success rate
   - State transition frequency

2. **Performance Metrics**
   - Response times (p50, p95, p99)
   - Request volume
   - Error rates
   - Timeout occurrences

3. **Resource Metrics**
   - CPU usage
   - Memory consumption
   - Thread pool utilization
   - Connection pool stats

### Monitoring Dashboard Example

```javascript
// Prometheus metrics example
const circuitBreakerMetrics = {
  state: new Gauge({
    name: 'circuit_breaker_state',
    help: 'Current state of circuit breaker',
    labelNames: ['service']
  }),
  
  failures: new Counter({
    name: 'circuit_breaker_failures_total',
    help: 'Total number of failures',
    labelNames: ['service']
  }),
  
  fallbacks: new Counter({
    name: 'circuit_breaker_fallbacks_total',
    help: 'Total number of fallback executions',
    labelNames: ['service']
  })
};
```

### Alerting Rules

```yaml
# Example alerting rules
alerts:
  - name: CircuitBreakerOpen
    condition: circuit_breaker_state{state="OPEN"} > 0
    duration: 5m
    severity: warning
    message: "Circuit breaker for {{ $labels.service }} has been open for 5 minutes"
    
  - name: HighFailureRate
    condition: rate(circuit_breaker_failures_total[5m]) > 0.5
    severity: critical
    message: "High failure rate detected for {{ $labels.service }}"
```

## 🤝 Contributing

We welcome contributions! Please see our contributing guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Standards

- Follow existing code style
- Add tests for new features
- Update documentation
- Ensure all tests pass
- Add meaningful commit messages

## 📚 Additional Resources

### Learn More About Circuit Breakers

- [Martin Fowler - Circuit Breaker](https://martinfowler.com/bliki/CircuitBreaker.html)
- [Microsoft - Circuit Breaker Pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/circuit-breaker)
- [Netflix Hystrix Documentation](https://github.com/Netflix/Hystrix/wiki)

### Reliability Engineering

- [Google SRE Book](https://sre.google/books/)
- [Release It! by Michael Nygard](https://pragprog.com/titles/mnee2/release-it-second-edition/)
- [Designing Data-Intensive Applications by Martin Kleppmann](https://dataintensive.net/)

### Tools and Libraries

- **Node.js**: [opossum](https://github.com/nodeshift/opossum)
- **Java**: [Resilience4j](https://resilience4j.readme.io/), [Hystrix](https://github.com/Netflix/Hystrix)
- **Python**: [pybreaker](https://github.com/danielfm/pybreaker)
- **.NET**: [Polly](https://github.com/App-vNext/Polly)
- **Go**: [gobreaker](https://github.com/sony/gobreaker)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Geniedev1** - Initial work and maintenance

## 🙏 Acknowledgments

- Circuit Breaker pattern pioneered by Michael Nygard
- Inspired by Netflix's resilience engineering practices
- Community contributors and testers

---

**Note**: This is a demonstration project for educational purposes. For production use, ensure proper testing, monitoring, and security measures are in place.