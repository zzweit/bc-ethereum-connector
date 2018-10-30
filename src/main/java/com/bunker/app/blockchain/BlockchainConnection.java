package com.bunker.app.blockchain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import rx.Subscription;

public class BlockchainConnection {
    private static final String PRIVATE_KEY = "0x21116800c560d1c28690ae74958aee6f227df8873530a2f4972fe68ff455007e";
    private static final String CONNECTION_URL = "http://127.0.0.1:7545";
    private static final String BUNKER_CONTRACT_ADDRESS = "0x1eec7bf25dc29655e699b38d1ce9cb2f3304d532";
//  private static final BigInteger GAS_PRICE = BigInteger.valueOf(2000000000);
//  private static final BigInteger GAS_LIMIT = BigInteger.valueOf(6721975);
    private Web3j web3j;
    private BunkerOrderStorage bunkerContract;
    private Credentials credentials;
    private Subscription subscription;
    
    private static String blockNumber = "";

    private final Logger log = LoggerFactory.getLogger(BlockchainConnection.class);
    
    public BunkerOrderStorage connectToBlockchain() {
    	try{
	    	this.web3j = Web3j.build(new HttpService(CONNECTION_URL));

//	    	Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
	        this.credentials = Credentials.create(PRIVATE_KEY);

//	        this.bunkerContract = BunkerOrderStorage.load(BUNKER_bunkerContract_ADDRESS, this.web3j, credentials, GAS_PRICE, GAS_LIMIT);
	        this.bunkerContract = BunkerOrderStorage.load(BUNKER_CONTRACT_ADDRESS, this.web3j, credentials, new DefaultGasProvider());
	        
	        observeLatestBlock(this.web3j);
	        return this.bunkerContract;
    	} catch(Exception e) {
    		log.info("Connection to Blockchain failed, Error:" + e.getMessage());
    		return this.bunkerContract;
    	} 
    }
    
    public void disconnectFromBlockchain() {
    	this.web3j.shutdown();
    }
    
    public Web3j getWeb3jClient(){
    	return this.web3j;
    }
    
    private void observeLatestBlock(Web3j web3Jclient) {
    	this.subscription = web3Jclient.blockObservable(false).subscribe(block -> {
    	   log.info("LATEST BLOCK NUMBER: " + block.getBlock().getNumber().toString());
    	   blockNumber = block.getBlock().getNumber().toString();
    	});
    }
    
    public void stopObserveLatestBlock(){
    	this.subscription.unsubscribe();
    } 
    
    public static String getBlockNumber() {
    	if(blockNumber.equals("")) {
    		return "";
    	} else {
    		return blockNumber;
    	}
    }   
}
