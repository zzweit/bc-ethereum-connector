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
    private static final String BINARY = "0x608060405234801561001057600080fd5b50610c69806100206000396000f300608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680634f8fed21146100675780635ac44282146100ce578063d06b1da8146101d1578063d439fd8a146101fe575b600080fd5b34801561007357600080fd5b506100cc6004803603810190808035906020019092919080359060200190929190803590602001908201803590602001919091929391929390803590602001908201803590602001919091929391929390505050610265565b005b3480156100da57600080fd5b506100f960048036038101908080359060200190929190505050610460565b604051808681526020018060200185815260200184815260200180602001838103835287818151815260200191508051906020019080838360005b8381101561014f578082015181840152602081019050610134565b50505050905090810190601f16801561017c5780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019060200280838360005b838110156101b857808201518184015260208101905061019d565b5050505090500197505050505050505060405180910390f35b3480156101dd57600080fd5b506101fc6004803603810190808035906020019092919050505061060f565b005b34801561020a57600080fd5b506102636004803603810190808035906020019092919080359060200190929190803590602001908201803590602001919091929391929390803590602001908201803590602001919091929391929390505050610721565b005b6000606060011515600160008a815260200190815260200160002060009054906101000a900460ff16151514151561029c57600080fd5b429150866000808a81526020019081526020016000206001018190555085856000808b815260200190815260200160002060020191906102dd9291906109e5565b5083836000808b81526020019081526020016000206004019190610302929190610a65565b50878260405160200180807f4261746368200000000000000000000000000000000000000000000000000000815250600601838152602001807f20686173206265656e2075706461746564206174200000000000000000000000815250601501828152602001925050506040516020818303038152906040529050877f817175c60fde6ce73a3d4456c376435f6b34f2fffc0f4599bc1f67f25540ba17833384604051808481526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b8381101561041a5780820151818401526020810190506103ff565b50505050905090810190601f1680156104475780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a25050505050505050565b600060606000806060610471610ab2565b600115156001600089815260200190815260200160002060009054906101000a900460ff1615151415156104a457600080fd5b60008088815260200190815260200160002060a060405190810160405290816000820154815260200160018201548152602001600282018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561056f5780601f106105445761010080835404028352916020019161056f565b820191906000526020600020905b81548152906001019060200180831161055257829003601f168201915b5050505050815260200160038201548152602001600482018054806020026020016040519081016040528092919081815260200182805480156105d157602002820191906000526020600020905b8154815260200190600101908083116105bd575b505050505081525050905080600001518160400151826020015183606001518460800151839350809050955095509550955095505091939590929450565b600115156001600083815260200190815260200160002060009054906101000a900460ff16151514151561064257600080fd5b60008082815260200190815260200160002060008082016000905560018201600090556002820160006106759190610ae2565b600382016000905560048201600061068d9190610b2a565b50506001600082815260200190815260200160002060006101000a81549060ff0219169055807f5e5c6f71ca1537714dcd89a8cc5f65f8a1a85d6ef0993877d718ab545d06234a4233604051808381526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019250505060405180910390a250565b610729610ab2565b6000606060001515600160008b815260200190815260200160002060009054906101000a900460ff16151514151561076057600080fd5b42915060a0604051908101604052808a815260200189815260200188888080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505081526020018381526020018686808060200260200160405190810160405280939291908181526020018383602002808284378201915050505050508152509250826000808b815260200190815260200160002060008201518160000155602082015181600101556040820151816002019080519060200190610831929190610b4b565b50606082015181600301556080820151816004019080519060200190610858929190610bcb565b5090505060018060008b815260200190815260200160002060006101000a81548160ff021916908315150217905550888260405160200180807f4261746368200000000000000000000000000000000000000000000000000000815250600601838152602001807f20686173206265656e2063726561746564206174200000000000000000000000815250601501828152602001925050506040516020818303038152906040529050887f817175c60fde6ce73a3d4456c376435f6b34f2fffc0f4599bc1f67f25540ba17833384604051808481526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b8381101561099e578082015181840152602081019050610983565b50505050905090810190601f1680156109cb5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a2505050505050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610a2657803560ff1916838001178555610a54565b82800160010185558215610a54579182015b82811115610a53578235825591602001919060010190610a38565b5b509050610a619190610c18565b5090565b828054828255906000526020600020908101928215610aa1579160200282015b82811115610aa0578235825591602001919060010190610a85565b5b509050610aae9190610c18565b5090565b60a06040519081016040528060008152602001600081526020016060815260200160008152602001606081525090565b50805460018160011615610100020316600290046000825580601f10610b085750610b27565b601f016020900490600052602060002090810190610b269190610c18565b5b50565b5080546000825590600052602060002090810190610b489190610c18565b50565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610b8c57805160ff1916838001178555610bba565b82800160010185558215610bba579182015b82811115610bb9578251825591602001919060010190610b9e565b5b509050610bc79190610c18565b5090565b828054828255906000526020600020908101928215610c07579160200282015b82811115610c06578251825591602001919060010190610beb565b5b509050610c149190610c18565b5090565b610c3a91905b80821115610c36576000816000905550600101610c1e565b5090565b905600a165627a7a723058206fb52ae0684cf865bcff4083e9e0b2743cd83b50052e74448a5e51eab3f6d61b0029";

    public static final String FUNC_CREATEBATCH = "createBatch";

    public static final String FUNC_UPDATEBATCH = "updateBatch";

    public static final String FUNC_GETBATCH = "getBatch";

    public static final String FUNC_DELETEBATCH = "deleteBatch";

    public static final Event BATCHUPDATED_EVENT = new Event("BatchUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event BATCHREMOVED_EVENT = new Event("BatchRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    public static String getAddresses() {
		return _addresses.values().iterator().next();
	}
    
    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("5777", "0x1eec7bf25dc29655e699b38d1ce9cb2f3304d532");
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
            typedResponse._dateUpdated = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse._dateUpdated = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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

    public List<BatchRemovedEventResponse> getBatchRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BATCHREMOVED_EVENT, transactionReceipt);
        ArrayList<BatchRemovedEventResponse> responses = new ArrayList<BatchRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BatchRemovedEventResponse typedResponse = new BatchRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._batchId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._dateRemoved = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._from = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<BatchRemovedEventResponse> batchRemovedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, BatchRemovedEventResponse>() {
            @Override
            public BatchRemovedEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BATCHREMOVED_EVENT, log);
                BatchRemovedEventResponse typedResponse = new BatchRemovedEventResponse();
                typedResponse.log = log;
                typedResponse._batchId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._dateRemoved = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._from = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<BatchRemovedEventResponse> batchRemovedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BATCHREMOVED_EVENT));
        return batchRemovedEventObservable(filter);
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

    public RemoteCall<TransactionReceipt> deleteBatch(BigInteger _batchId) {
        final Function function = new Function(
                FUNC_DELETEBATCH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_batchId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

        public BigInteger _dateUpdated;

        public String _from;

        public String state;
    }

    public static class BatchRemovedEventResponse {
        public Log log;

        public BigInteger _batchId;

        public BigInteger _dateRemoved;

        public String _from;
    }
}
