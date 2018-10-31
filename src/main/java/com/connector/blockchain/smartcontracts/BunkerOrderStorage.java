package com.connector.blockchain.smartcontracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.6.0.
 */

// **BUNKER ORDER STORAGE SMART CONTRACT IN JAVA**
public class BunkerOrderStorage extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b50610a0e806100206000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806320ba1a131461005c5780632da918b5146100d1578063392f72611461024f575b600080fd5b34801561006857600080fd5b506100cf600480360381019080803590602001909291908035906020019082018035906020019190919293919293908035906020019082018035906020019190919293919293908035906020019082018035906020019190919293919293905050506102c4565b005b3480156100dd57600080fd5b506100fc600480360381019080803590602001909291905050506104fb565b60405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b83811015610144578082015181840152602081019050610129565b50505050905090810190601f1680156101715780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b838110156101aa57808201518184015260208101905061018f565b50505050905090810190601f1680156101d75780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b838110156102105780820151818401526020810190506101f5565b50505050905090810190601f16801561023d5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b34801561025b57600080fd5b506102c260048036038101908080359060200190929190803590602001908201803590602001919091929391929390803590602001908201803590602001919091929391929390803590602001908201803590602001919091929391929390505050610753565b005b6102cc610894565b60001515600160008a815260200190815260200160002060009054906101000a900460ff1615151415156102ff57600080fd5b60806040519081016040528089815260200188888080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050815260200186868080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050815260200184848080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050508152509050806000808a81526020019081526020016000206000820151816000015560208201518160010190805190602001906103f29291906108bd565b50604082015181600201908051906020019061040f9291906108bd565b50606082015181600301908051906020019061042c9291906108bd565b5090505060018060008a815260200190815260200160002060006101000a81548160ff021916908315150217905550877f5bdb1f95753ae8685fbef0ae7eac39fab7acd69cee6cd1f233ec1aee629f77658888888888886040518080602001806020018060200184810384528a8a8281815260200192508082843782019150508481038352888882818152602001925080828437820191505084810382528686828181526020019250808284378201915050995050505050505050505060405180910390a25050505050505050565b6060806060600115156001600086815260200190815260200160002060009054906101000a900460ff16151514151561053357600080fd5b600080858152602001908152602001600020600101600080868152602001908152602001600020600201600080878152602001908152602001600020600301828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106075780601f106105dc57610100808354040283529160200191610607565b820191906000526020600020905b8154815290600101906020018083116105ea57829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106a35780601f10610678576101008083540402835291602001916106a3565b820191906000526020600020905b81548152906001019060200180831161068657829003601f168201915b50505050509150808054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561073f5780601f106107145761010080835404028352916020019161073f565b820191906000526020600020905b81548152906001019060200180831161072257829003601f168201915b505050505090509250925092509193909250565b600115156001600089815260200190815260200160002060009054906101000a900460ff16151514151561078657600080fd5b85856000808a815260200190815260200160002060010191906107aa92919061093d565b5083836000808a815260200190815260200160002060020191906107cf92919061093d565b5081816000808a815260200190815260200160002060030191906107f492919061093d565b50867f5bdb1f95753ae8685fbef0ae7eac39fab7acd69cee6cd1f233ec1aee629f77658787878787876040518080602001806020018060200184810384528a8a8281815260200192508082843782019150508481038352888882818152602001925080828437820191505084810382528686828181526020019250808284378201915050995050505050505050505060405180910390a250505050505050565b608060405190810160405280600081526020016060815260200160608152602001606081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106108fe57805160ff191683800117855561092c565b8280016001018555821561092c579182015b8281111561092b578251825591602001919060010190610910565b5b50905061093991906109bd565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061097e57803560ff19168380011785556109ac565b828001600101855582156109ac579182015b828111156109ab578235825591602001919060010190610990565b5b5090506109b991906109bd565b5090565b6109df91905b808211156109db5760008160009055506001016109c3565b5090565b905600a165627a7a7230582092c143f03a255756fbec0bd82062babaace1fb2833f5fd8de82fd3a686d5ab040029";

    public static final String FUNC_CREATEBUNKERORDER = "createBunkerOrder";

    public static final String FUNC_UPDATEBUNKERORDER = "updateBunkerOrder";

    public static final String FUNC_GETBUNKERORDER = "getBunkerOrder";

    public static final Event ORDERUPDATED_EVENT = new Event("OrderUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("5777", "0x1eec7bf25dc29655e699b38d1ce9cb2f3304d532");
    }

    @Deprecated
    protected BunkerOrderStorage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BunkerOrderStorage(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BunkerOrderStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BunkerOrderStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<OrderUpdatedEventResponse> getOrderUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ORDERUPDATED_EVENT, transactionReceipt);
        ArrayList<OrderUpdatedEventResponse> responses = new ArrayList<OrderUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OrderUpdatedEventResponse typedResponse = new OrderUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.orderId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.supplyingOrg = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.receivingOrg = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.testResults = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OrderUpdatedEventResponse> orderUpdatedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, OrderUpdatedEventResponse>() {
            @Override
            public OrderUpdatedEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ORDERUPDATED_EVENT, log);
                OrderUpdatedEventResponse typedResponse = new OrderUpdatedEventResponse();
                typedResponse.log = log;
                typedResponse.orderId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.supplyingOrg = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.receivingOrg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.testResults = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<OrderUpdatedEventResponse> orderUpdatedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ORDERUPDATED_EVENT));
        return orderUpdatedEventObservable(filter);
    }

    public RemoteCall<TransactionReceipt> createBunkerOrder(BigInteger _orderId, String _supplyingOrg, String _receivingOrg, String _testResults) {
        final Function function = new Function(
                FUNC_CREATEBUNKERORDER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderId), 
                new org.web3j.abi.datatypes.Utf8String(_supplyingOrg), 
                new org.web3j.abi.datatypes.Utf8String(_receivingOrg), 
                new org.web3j.abi.datatypes.Utf8String(_testResults)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> updateBunkerOrder(BigInteger _orderId, String _supplyingOrg, String _receivingOrg, String _testResults) {
        final Function function = new Function(
                FUNC_UPDATEBUNKERORDER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderId), 
                new org.web3j.abi.datatypes.Utf8String(_supplyingOrg), 
                new org.web3j.abi.datatypes.Utf8String(_receivingOrg), 
                new org.web3j.abi.datatypes.Utf8String(_testResults)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple3<String, String, String>> getBunkerOrder(BigInteger _orderId) {
        final Function function = new Function(FUNC_GETBUNKERORDER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple3<String, String, String>>(
                new Callable<Tuple3<String, String, String>>() {
                    @Override
                    public Tuple3<String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
    }

    public static RemoteCall<BunkerOrderStorage> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BunkerOrderStorage.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<BunkerOrderStorage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BunkerOrderStorage.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<BunkerOrderStorage> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BunkerOrderStorage.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<BunkerOrderStorage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BunkerOrderStorage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static BunkerOrderStorage load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BunkerOrderStorage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BunkerOrderStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BunkerOrderStorage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BunkerOrderStorage load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new BunkerOrderStorage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BunkerOrderStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BunkerOrderStorage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class OrderUpdatedEventResponse {
        public Log log;

        public BigInteger orderId;

        public String supplyingOrg;

        public String receivingOrg;

        public String testResults;
    }
}
