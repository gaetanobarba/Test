# Exercise 3

Implement a product service that exposes the following endpoints:
* POST
	- addProduct(...) : takes a product as parameter, stores it in a cache / in-memory database (up to you!).
						The updatedAt field must be filled automatically by the system with format yyyy-mm-dd. Price and quantity can't be < 0.
* GET
	- getAll() : get all products
	- getProductById(...) : takes a productId as parameter and returns the entire entity
	- getAllProductByCategory(...) : takes a category as parameter and returns all products belonging to that category
	- getAllProductsGroupedByCategory() : returns all products grouped by category
* PUT
	- updateProduct(...) :  takes a productId and a price or/and a quantity and updates the product if exists, otherwise returns an error.
							Endpoints can be either REST or gRPC, your choice

## Maven commands for package creation

```bash
cd C:\Users\teigaba\git\Test\Exercise3\
mvn clean package

cd C:\Users\teigaba\git\Test\Exercise3\target

```

## GRPC Server run command
```bash
java -cp "./lib/*:Exercise3-0.0.1-SNAPSHOT.jar" com.excercise3.grpc.MyGrpcServer
```

## GRPC Client run command
```bash
java -cp "./lib/*:Exercise3-0.0.1-SNAPSHOT.jar" com.excercise3.grpc.MyGrpcClient
```


