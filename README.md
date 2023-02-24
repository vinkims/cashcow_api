# CASHCOW 

An API to provide backend support to the CashCow platform.
It is a cow farm management service.


## Deployment

### Prerequisites
    - Java 11
    - Maven
    - Postgresql

### Development/local
- Create a `cashcow_dev` database on postgres.
- Clone the repository: `https://github.com/vinkims/cashcow_api.git`
- cd into the directory.
- Fetch and checkout the `develop` branch: `git fetch origin develop` & `git checkout develop`
- Once in the root directory, you can either startup the API directly or build it first.
#### Direct
- Update the `database` and `flyway` credentials as stated in the `src/main/resources/application.properties` so they match your local settings.
- Run the cmd: `mvn spring-boot:run` to start up the API.
- Once the API is running, you can access the logs in the `logs` directory.
#### Java build
- Create a copy of the `src/main/resources/application.properties` file and in it set the `database` and `flyway` credentials to match your local ones.
- Build the project using the cmd: `mvn clean package`
- Once the build is complete, run the resulting jar file as stored in the `target` directory. Use the command: `java -jar $jar_path -- spring.config.location=$prop_file` where prop_file is the path to the application.properties copy you made earlier.


## Usage

### Requests
All requests/endpoints require an authentication token to be passed in the header except for:
    - "/user/auth"

#### Pagination
For the paginated endpoints, the following can be specified as url query paramaters:
- pgNum : The page number to be returned `[Default: 0]`
- pgSize : Number of elements per page `[Default: 10]`
- sortValue : The column/field to be used for sorting/ordering results `[Default: createdOn]`
- sortDirection : The order of results to be returned; can either be *asc* or *desc* `[Default: desc]`

    Example: 
    `/user?pgNum=1&pgSize=20`

#### Filtering/Searching
To filter a list based on resource fields/ columns, the below can be used as URL query parameters.
The query parameter should be in the format: `keyOperatorValue`. These can be chained via comas. Operator can either be:
- EQ - equals
- GT - greater than or equal to
- LT - less than or equal to
- NIN - not equal to


*Users*

    - id
    - firstName
    - middleName
    - lastName
    - createdOn
    - farm.id
    - role.id
    - shop.id
    - status.id

Example:
- `/user?q=firstNameEQJohn` -> returns all users with specified first name
- `/user?q=status.idEQ1,shop.idEQ3` -> returns all users with the specified status id and shop id

*Cows*

    - id
    - name
    - createdOn
    - farm.id
    - parent.id
    - status.id
    - profile.breed
    - profile.color
    - profile.gender
    - profile.dateOfBirth
    - profile.dateOfPurchase
    - profile.dateOfSale
    - profile.dateOfDeath
    - profile.locationBought
    - profile.purchaseAmount
    - profile.saleAmount

Example `cow?q=parent.id=1` -> returns all cows with the specified parent id

*Transactions*

    - id
    - transactionCode
    - amount
    - createdOn
    - reference
    - attendant.id
    - customer.id
    - paymentChannel.id
    - shop.id
    - status.id
    - transactionType.id

Example `transaction?q=referenceEQ245700112233` -> returns all transactions with the specified reference
