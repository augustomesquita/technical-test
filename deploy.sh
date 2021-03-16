# Create network for containers
echo -e "Creating network which will be used by containers..."
docker network create technical-test-network
echo -e "Network created"

# Create network for containers
echo -e "\nCreating container for MySQL 8.0..."
docker run -d -p 3306:3306 --name=stock-quote-manager-db --network=technical-test-network --env="MYSQL_USER=spring-user" --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_PASSWORD=secret" --env="MYSQL_DATABASE=bootdb" mysql:8.0
echo -e "The mysql's container was built successfully."

# Create container for stock-manager-api
echo -e "\nCreating container for stock-manager-api..."
cd stock-manager
mvn clean package -DskipTests
mvn clean package docker:build -DskipTests
docker run -d -p 8080:8080 --name=stock-manager-api --network=technical-test-network docker.lazydev.com/stock-manager:latest
echo -e "The stock-manager-api's container was built successfully."

# Create container for stock-quote-manager-api
echo -e "\nCreating container for stock-quote-manager-api..."
cd ..
cd stock-quote-manager
mvn clean package -DskipTests
mvn clean package docker:build -DskipTests
docker run -d -p 8081:8081 --name=stock-quote-manager-api --network=technical-test-network -e MYSQL_ADDR='stock-quote-manager-db' -e STOCK_MANAGER_HOST='stock-manager-api' -e STOCK_MANAGER_PORT='8080' -e STOCK_QUOTE_MANAGER_HOST='stock-quote-manager-api' -e STOCK_QUOTE_MANAGER_PORT='8081' docker.lazydev.com/stock-quote-manager:latest
echo -e "The stock-quote-manager-api built successfully."