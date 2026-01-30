# **_PROJECT Overview_**

A secure authentication service focused on defensive engineering practices such as password hashing, logging hygiene, and structured logging to prevent credential leakage.

## Security Features

* bcrypt password hashing (jBCrypt)
* Centralized secure logging
* Structured logging with field level masking
* Explicit threat modeling

## Design Decisions

* Why structured logging over regex-only sanitization
* Why passwords are never logged or stored in plaintext
* Tradeoffs (e.g., in-memory structures vs production-grade infra)

## Testing

* Unit tests proving sensitive data never appears in logs

## Future Enhancements

* Rate limiting & brute-force protection
* Token-based authentication
* Attack simulation
* Cloud deployment considerations