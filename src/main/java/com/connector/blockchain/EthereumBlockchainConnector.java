package com.connector.blockchain;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import com.connector.blockchain.BlockchainProperties.PropertyTypes;
import com.connector.blockchain.smartcontracts.BunkerOrderStorage;
import com.connector.blockchain.smartcontracts.BunkerSupplyChainTransaction;
import com.connector.blockchain.smartcontracts.SupplyChain;

// PLEASE NOTE 
public class EthereumBlockchainConnector {

    private final Logger log = LoggerFactory.getLogger(EthereumBlockchainConnector.class);
	
//	Blockchain Required Properties
    private static BlockchainProperties blockProps = new BlockchainProperties();
    private static final String CONNECTION_URL = blockProps.getProperties(PropertyTypes.CONNECTION_URL);
    private static final String BUNKER_CONTRACT_ADDRESS = blockProps.getProperties(PropertyTypes.BUNKER_STORAGE_CONTRACT_ADDRESS);
    private static final String SUPPLY_CHAIN_CONTRACT_ADDRESS = blockProps.getProperties(PropertyTypes.SUPPLY_CHAIN_CONTRACT_ADDRESS);
    private static final String BUNKER_SUPPLY_CHAIN_V1_CONTRACT_ADDRESS = blockProps.getProperties(PropertyTypes.BUNKER_SUPPLY_CHAIN_V1_CONTRACT_ADDRESS);

//  Sample Ganache Private Key   
    private static final String PRIVATE_KEY = "0x21116800c560d1c28690ae74958aee6f227df8873530a2f4972fe68ff455007e";

//  Types of Smart Contract
	public enum ContractType {
		BUNKER_STORAGE_CONTRACT,
		SUPPLY_CHAIN_CONTRACT,
		BUNKER_SUPPLY_CHAIN_V1_CONTRACT
	}
	
	private Web3j web3j;
	
	public void connect() {
		this.web3j = Web3j.build(new HttpService(CONNECTION_URL));
	}
	
	public void connect(String connectionUrl) {
		this.web3j = Web3j.build(new HttpService(connectionUrl));
	}
	
	public void disconnect() {
		this.web3j.shutdown();
	}
	
	public Contract getContract(String privateKey, ContractType contractType) {
		Credentials credentials = Credentials.create(privateKey);
		Contract contract = null;
		switch(contractType) {
			case BUNKER_STORAGE_CONTRACT:
		        contract = BunkerOrderStorage.load(BUNKER_CONTRACT_ADDRESS, this.web3j, credentials, new StaticGasProvider(BigInteger.valueOf(0), BigInteger.valueOf(4500000)));
		        break;
			case SUPPLY_CHAIN_CONTRACT:
		        contract = SupplyChain.load(SUPPLY_CHAIN_CONTRACT_ADDRESS, this.web3j, credentials, new DefaultGasProvider());
				break;
			case BUNKER_SUPPLY_CHAIN_V1_CONTRACT:
		        contract = BunkerSupplyChainTransaction.load(BUNKER_SUPPLY_CHAIN_V1_CONTRACT_ADDRESS, this.web3j, credentials,  new StaticGasProvider(BigInteger.valueOf(0), BigInteger.valueOf(4500000)));
		        break;
		}		
		return contract;				
	}
	
	public String getWeb3ClientVersion() throws ConnectException, NullPointerException, IOException {
				
		String web3ClientVersionString = null;
		Web3ClientVersion web3ClientVersion = null;

		web3ClientVersion = this.web3j.web3ClientVersion().send();

		if (web3ClientVersion != null)
			web3ClientVersionString = web3ClientVersion.getWeb3ClientVersion();
		
		return web3ClientVersionString;
	}

//	Unable to find a proper API method in Web3J to test connection 
	public boolean isConnected() {
		try {
			this.web3j.web3ClientVersion().send();
			return true;
		} catch (ConnectException e) {
			return false;
		} catch (IOException e) {
			return false;
		} 
	}

//    		#TEST#   	
/*
	public static void main(String[] args) {
		System.out.println("Connection URL: " + CONNECTION_URL);
		System.out.println("BUNKER SMART CONTRACT ADDRESS: " + BUNKER_CONTRACT_ADDRESS);
	    System.out.println("SUPPLY CHAIN SMART CONTRACT ADDRESS: " + SUPPLY_CHAIN_CONTRACT_ADDRESS);
	
		EthereumBlockchainConnector connector = new EthereumBlockchainConnector();
		System.out.println("Web3J: " + connector.web3j);
		System.out.println("CONNECTION TEST:" + connector.isConnected());
		
// 		TYPECAST TO THE CONTRACT REQUIRED 		
		BunkerOrderStorage bunkerContract = (BunkerOrderStorage) connector.getContract(PRIVATE_KEY, ContractType.BUNKER_STORAGE_CONTRACT);
		SupplyChain supplyChainContract = (SupplyChain) connector.getContract(PRIVATE_KEY, ContractType.SUPPLY_CHAIN_CONTRACT);

// 		TEST CREATING TRANSACTION TO BUNKER_ORDER_STORAGE	
//		TransactionReceipt transactionReceipt = null;		
//		try {
//			transactionReceipt = bunkerContract.createBunkerOrder(BigInteger.valueOf(89), "TEST", "TEST", "A").send();
//		} catch (Exception e) {
//			System.out.println("MESSAGE: " + e.getMessage());
//		}
//    	System.out.println("Blockchain Transaction Create Receipt: " + transactionReceipt); 

//		TEST CREATING TRANSACTION TO SUPPLY_CHAIN 
//		TransactionReceipt transactionReceipt = null;
//		try {
//			List<BigInteger> list = new ArrayList<>();
//			transactionReceipt = supplyChainContract.updateBatch(BigInteger.valueOf(12335), BigInteger.valueOf(20), "ABC", list).send();

//			Test Get
//			System.out.println(supplyChainContract.getBatch(BigInteger.valueOf(1234)).send());
//		} catch (Exception e) {
//			System.out.println("MESSAGE: " + e);
//		}
//    	System.out.println("Blockchain Transaction Create Receipt: " + transactionReceipt); 

    	connector.disconnectFromBlockchain();
	}
*/
}
		

