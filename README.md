# Ethereum Blockchain Connector

### Maven Dependencies

Install jar file in your local Maven repository by running the following command in the project directory,

```
$ mvn install:install-file \
   -Dfile=<insert-path-to-jar-file> \
   -DgroupId=com.ncs.blockchain.ethereum \
   -DartifactId=blockchain-ethereum-connector \
   -Dversion=0.0.1-SNAPSHOT \
   -Dpackaging=jar \
   -DgeneratePom=true
```

Include in Java application pom.xml,

```xml
<repository>
    <id> *id-of-local-maven-repo* </id>
    <url> *path-to-local-maven-repo* </url>
</repository>
```

```xml
<dependency>
    <groupId>com.ncs.blockchain.ethereum</groupId>
    <artifactId>blockchain-ethereum-connector</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

To import in Java class,
`import com.connector.blockchain.EthereumBlockchainConnector;`

### Smart Contract

- Sample Solidity and Java contract class is stored in folder _src/main/java/com/connector/blockchain/smartcontracts_

### Configuration for blockchain

- Settings for Ethereum RPC endpoint URL and Smart Contract addresses can be edited in _/src/main/resources/config.properties_ file.
- Code can be editted in **BlockchainProperties.java** at _/src/main/java/com/connector/blockchain_
