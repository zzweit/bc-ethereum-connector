package com.connector.blockchain;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.DefaultGasProvider;

import com.connector.blockchain.BlockchainProperties.PropertyTypes;
import com.connector.blockchain.smartcontracts.BunkerOrderStorage;

public class EthereumBlockchainConnector {

//	Blockchain Required Properties
    private static BlockchainProperties blockProps = new BlockchainProperties();
    private static final String CONNECTION_URL = blockProps.getProperties(PropertyTypes.CONNECTION_URL);
    private static final String BUNKER_CONTRACT_ADDRESS = blockProps.getProperties(PropertyTypes.BUNKER_STORAGE_CONTRACT_ADDRESS);
	
//  Sample Ganache Private Key   
//  private static final String PRIVATE_KEY = "0x21116800c560d1c28690ae74958aee6f227df8873530a2f4972fe68ff455007e";

//  Types of smart contracts
	public enum ContractType {
		BUNKER_STORAGE_CONTRACT,
		TEST_CONTRACT1,
		TEST_CONTRACT2
	}
	
	private Web3j web3j;

	public EthereumBlockchainConnector() {
		this.web3j = Web3j.build(new HttpService(CONNECTION_URL));
	}
	
	public EthereumBlockchainConnector(String connectionUrl) {
		this.web3j = Web3j.build(new HttpService(connectionUrl));
	}
	
	public void reconnectToBlockchain() {
		this.web3j = Web3j.build(new HttpService(CONNECTION_URL));
	}
	
	public void reconnectToBlockchain(String connectionUrl) {
		this.web3j = Web3j.build(new HttpService(connectionUrl));
	}
	
	public void disconnectFromBlockchain() {
		this.web3j.shutdown();
	}
	
	public Web3j getWeb3jClient() {
		return this.web3j;
	}
	
	public Contract getContract(String privateKey, ContractType contractType) {
		Credentials credentials = Credentials.create(privateKey);
		Contract contract = null;
		switch(contractType) {
			case BUNKER_STORAGE_CONTRACT:
		        contract = BunkerOrderStorage.load(BUNKER_CONTRACT_ADDRESS, this.web3j, credentials, new DefaultGasProvider());
		        break;
			case TEST_CONTRACT1:
				// LOAD ANOTHER SMART CONTRACT
				break;
			case TEST_CONTRACT2:
				// LOAD ANOTHER SMART CONTRACT
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
//  TODO need to re-write this portion
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
	
/*    		#TEST#   		*/
//	public static void main(String[] args) {
//		System.out.println("Connection URL: " + CONNECTION_URL);
//		System.out.println("BUNKER SMART CONTRACT ADDRESS: " + BUNKER_CONTRACT_ADDRESS);
//
//		EthereumBlockchainConnector test = new EthereumBlockchainConnector();
//		System.out.println("Web3J: " + test.web3j);
//		System.out.println("CONNECTION TEST:" + test.isConnected());
		
/* 		TYPECAST TO THE CONTRACT REQUIRED 		*/
//		BunkerOrderStorage contract = (BunkerOrderStorage) test.getContract(PRIVATE_KEY, ContractType.BUNKER_STORAGE_CONTRACT);

/* 		TEST CONNECTION TO BLOCKCHAIN		*/
//		try {
//			System.out.println(test.getWeb3ClientVersion());
//		} catch (ConnectException e) {
//			System.out.println("CAUGHT Connect Exception");
//		} catch (NullPointerException e) {
//			System.out.println("CAUGHT Null Pointer Exception");
//		} catch (IOException e) {
//			System.out.println("Caught IO");
//		}
		
/* 		TEST CREATING TRANSACTION TO BLOCKCHAIN		*/
//		TransactionReceipt transactionReceipt = null;		
//		try {
//			transactionReceipt = contract.createBunkerOrder(BigInteger.valueOf(89), "TEST", "TEST", "A").send();
//		} catch (Exception e) {
//			System.out.println("MESSAGE: " + e.getMessage());
//		}
//    	System.out.println("Blockchain Transaction Create Receipt: " + transactionReceipt);
//		test.disconnectFromBlockchain();
//	}
		
}
		

