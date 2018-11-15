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
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
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
import org.web3j.tuples.generated.Tuple5;
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
public class SupplyChain extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b50610b62806100206000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680634f8fed211461005c5780635ac44282146100c3578063d439fd8a146101c6575b600080fd5b34801561006857600080fd5b506100c1600480360381019080803590602001909291908035906020019092919080359060200190820180359060200191909192939192939080359060200190820180359060200191909192939192939050505061022d565b005b3480156100cf57600080fd5b506100ee60048036038101908080359060200190929190505050610436565b604051808681526020018060200185815260200184815260200180602001838103835287818151815260200191508051906020019080838360005b83811015610144578082015181840152602081019050610129565b50505050905090810190601f1680156101715780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019060200280838360005b838110156101ad578082015181840152602081019050610192565b5050505090500197505050505050505060405180910390f35b3480156101d257600080fd5b5061022b60048036038101908080359060200190929190803590602001909291908035906020019082018035906020019190919293919293908035906020019082018035906020019190919293919293905050506105e5565b005b6000606060011515600160008a815260200190815260200160002060009054906101000a900460ff16151514151561026457600080fd5b429150866000808a81526020019081526020016000206001018190555085856000808b815260200190815260200160002060020191906102a5929190610947565b506000848490501415156102d95783836000808b815260200190815260200160002060040191906102d79291906109c7565b505b878260405160200180807f4261746368200000000000000000000000000000000000000000000000000000815250600601838152602001807f20686173206265656e2075706461746564206174200000000000000000000000815250601501828152602001925050506040516020818303038152906040529050877f817175c60fde6ce73a3d4456c376435f6b34f2fffc0f4599bc1f67f25540ba17833384604051808481526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b838110156103f05780820151818401526020810190506103d5565b50505050905090810190601f16801561041d5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a25050505050505050565b600060606000806060610447610a14565b600115156001600089815260200190815260200160002060009054906101000a900460ff16151514151561047a57600080fd5b60008088815260200190815260200160002060a060405190810160405290816000820154815260200160018201548152602001600282018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105455780601f1061051a57610100808354040283529160200191610545565b820191906000526020600020905b81548152906001019060200180831161052857829003601f168201915b5050505050815260200160038201548152602001600482018054806020026020016040519081016040528092919081815260200182805480156105a757602002820191906000526020600020905b815481526020019060010190808311610593575b505050505081525050905080600001518160400151826020015183606001518460800151839350809050955095509550955095505091939590929450565b6105ed610a14565b6000606060001515600160008b815260200190815260200160002060009054906101000a900460ff16151514151561062457600080fd5b42915060008585905014156106c45760a0604051908101604052808a815260200189815260200188888080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050815260200183815260200160006040519080825280602002602001820160405280156106b95781602001602082028038833980820191505090505b508152509250610750565b60a0604051908101604052808a815260200189815260200188888080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050815260200183815260200186868080602002602001604051908101604052809392919081815260200183836020028082843782019150505050505081525092505b826000808b815260200190815260200160002060008201518160000155602082015181600101556040820151816002019080519060200190610793929190610a44565b506060820151816003015560808201518160040190805190602001906107ba929190610ac4565b5090505060018060008b815260200190815260200160002060006101000a81548160ff021916908315150217905550888260405160200180807f4261746368200000000000000000000000000000000000000000000000000000815250600601838152602001807f20686173206265656e2063726561746564206174200000000000000000000000815250601501828152602001925050506040516020818303038152906040529050887f817175c60fde6ce73a3d4456c376435f6b34f2fffc0f4599bc1f67f25540ba17833384604051808481526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b838110156109005780820151818401526020810190506108e5565b50505050905090810190601f16801561092d5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a2505050505050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061098857803560ff19168380011785556109b6565b828001600101855582156109b6579182015b828111156109b557823582559160200191906001019061099a565b5b5090506109c39190610b11565b5090565b828054828255906000526020600020908101928215610a03579160200282015b82811115610a025782358255916020019190600101906109e7565b5b509050610a109190610b11565b5090565b60a06040519081016040528060008152602001600081526020016060815260200160008152602001606081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610a8557805160ff1916838001178555610ab3565b82800160010185558215610ab3579182015b82811115610ab2578251825591602001919060010190610a97565b5b509050610ac09190610b11565b5090565b828054828255906000526020600020908101928215610b00579160200282015b82811115610aff578251825591602001919060010190610ae4565b5b509050610b0d9190610b11565b5090565b610b3391905b80821115610b2f576000816000905550600101610b17565b5090565b905600a165627a7a72305820929641d3ac7096882880472c61a8b341af972cc076f2821ffdd47efeeb00e9280029";

    public static final String FUNC_CREATEBATCH = "createBatch";

    public static final String FUNC_UPDATEBATCH = "updateBatch";

    public static final String FUNC_GETBATCH = "getBatch";

    public static final Event BATCHUPDATED_EVENT = new Event("BatchUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("5777", "0x1eec7bf25dc29655e699b38d1ce9cb2f3304d532");
    }
    
    public static String getAddresses() {
		return _addresses.values().iterator().next();
	}

    @Deprecated
    protected SupplyChain(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SupplyChain(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SupplyChain(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SupplyChain(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<BatchUpdatedEventResponse> getBatchUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BATCHUPDATED_EVENT, transactionReceipt);
        ArrayList<BatchUpdatedEventResponse> responses = new ArrayList<BatchUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BatchUpdatedEventResponse typedResponse = new BatchUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._batchId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._dateCreated = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._from = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.state = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<BatchUpdatedEventResponse> batchUpdatedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, BatchUpdatedEventResponse>() {
            @Override
            public BatchUpdatedEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BATCHUPDATED_EVENT, log);
                BatchUpdatedEventResponse typedResponse = new BatchUpdatedEventResponse();
                typedResponse.log = log;
                typedResponse._batchId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._dateCreated = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._from = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.state = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<BatchUpdatedEventResponse> batchUpdatedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BATCHUPDATED_EVENT));
        return batchUpdatedEventObservable(filter);
    }

    public RemoteCall<TransactionReceipt> createBatch(BigInteger _batchId, BigInteger _quantity, String _description, List<BigInteger> _sourceBatchIds) {
        final Function function = new Function(
                FUNC_CREATEBATCH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_batchId), 
                new org.web3j.abi.datatypes.generated.Uint256(_quantity), 
                new org.web3j.abi.datatypes.Utf8String(_description), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.Utils.typeMap(_sourceBatchIds, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> updateBatch(BigInteger _batchId, BigInteger _quantity, String _description, List<BigInteger> _sourceBatchIds) {
        final Function function = new Function(
                FUNC_UPDATEBATCH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_batchId), 
                new org.web3j.abi.datatypes.generated.Uint256(_quantity), 
                new org.web3j.abi.datatypes.Utf8String(_description), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.Utils.typeMap(_sourceBatchIds, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple5<BigInteger, String, BigInteger, BigInteger, List<BigInteger>>> getBatch(BigInteger _batchId) {
        final Function function = new Function(FUNC_GETBATCH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_batchId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteCall<Tuple5<BigInteger, String, BigInteger, BigInteger, List<BigInteger>>>(
                new Callable<Tuple5<BigInteger, String, BigInteger, BigInteger, List<BigInteger>>>() {
                    @Override
                    public Tuple5<BigInteger, String, BigInteger, BigInteger, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, String, BigInteger, BigInteger, List<BigInteger>>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                convertToNative((List<Uint256>) results.get(4).getValue()));
                    }
                });
    }

    public static RemoteCall<SupplyChain> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SupplyChain.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SupplyChain> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SupplyChain.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<SupplyChain> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SupplyChain.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SupplyChain> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SupplyChain.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static SupplyChain load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SupplyChain(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SupplyChain load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SupplyChain(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SupplyChain load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SupplyChain(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SupplyChain load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SupplyChain(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class BatchUpdatedEventResponse {
        public Log log;

        public BigInteger _batchId;

        public BigInteger _dateCreated;

        public String _from;

        public String state;
    }
}
