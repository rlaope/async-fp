curl -X POST http://localhost:8080/log \
  -H "Content-Type: application/json" \
  -d '{
    "timestamp": "2025-04-12T17:00:00",
    "level": "INFO",
    "service": "user-api",
    "message": "User login success",
    "meta": { "userId": "abc123" }
}'