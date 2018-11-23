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
    private static final String BINARY = "0x608060405234801561001057600080fd5b50610d4b806100206000396000f300608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680634f8fed21146100675780635ac44282146100ce578063d06b1da8146101d1578063d439fd8a146101fe575b600080fd5b34801561007357600080fd5b506100cc6004803603810190808035906020019092919080359060200190929190803590602001908201803590602001919091929391929390803590602001908201803590602001919091929391929390505050610265565b005b3480156100da57600080fd5b506100f9600480360381019080803590602001909291905050506103d3565b604051808681526020018060200185815260200184815260200180602001838103835287818151815260200191508051906020019080838360005b8381101561014f578082015181840152602081019050610134565b50505050905090810190601f16801561017c5780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019060200280838360005b838110156101b857808201518184015260208101905061019d565b5050505090500197505050505050505060405180910390f35b3480156101dd57600080fd5b506101fc60048036038101908080359060200190929190505050610582565b005b34801561020a57600080fd5b50610263600480360381019080803590602001909291908035906020019092919080359060200190820180359060200191909192939192939080359060200190820180359060200191909192939192939050505061088b565b005b600115156001600088815260200190815260200160002060009054906101000a900460ff16151514151561029857600080fd5b8460008088815260200190815260200160002060010181905550838360008089815260200190815260200160002060020191906102d6929190610ac7565b50818160008089815260200190815260200160002060040191906102fb929190610b47565b50857fcf3d2eef4f86b6891897e961560d385d7790d429d77dbf513cb828b2f8234ef54233888888604051808681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018481526020018060200180602001838103835285858281815260200192508082843782019150508381038252600c8152602001807f426174636855706461746564000000000000000000000000000000000000000081525060200197505050505050505060405180910390a2505050505050565b6000606060008060606103e4610b94565b600115156001600089815260200190815260200160002060009054906101000a900460ff16151514151561041757600080fd5b60008088815260200190815260200160002060a060405190810160405290816000820154815260200160018201548152602001600282018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104e25780601f106104b7576101008083540402835291602001916104e2565b820191906000526020600020905b8154815290600101906020018083116104c557829003601f168201915b50505050508152602001600382015481526020016004820180548060200260200160405190810160405280929190818152602001828054801561054457602002820191906000526020600020905b815481526020019060010190808311610530575b505050505081525050905080600001518160400151826020015183606001518460800151839350809050955095509550955095505091939590929450565b61058a610b94565b600115156001600084815260200190815260200160002060009054906101000a900460ff1615151415156105bd57600080fd5b60008083815260200190815260200160002060a060405190810160405290816000820154815260200160018201548152602001600282018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106885780601f1061065d57610100808354040283529160200191610688565b820191906000526020600020905b81548152906001019060200180831161066b57829003601f168201915b5050505050815260200160038201548152602001600482018054806020026020016040519081016040528092919081815260200182805480156106ea57602002820191906000526020600020905b8154815260200190600101908083116106d6575b505050505081525050905060008083815260200190815260200160002060008082016000905560018201600090556002820160006107289190610bc4565b60038201600090556004820160006107409190610c0c565b50506001600083815260200190815260200160002060006101000a81549060ff0219169055817fcf3d2eef4f86b6891897e961560d385d7790d429d77dbf513cb828b2f8234ef5423384602001518560400151604051808581526020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018381526020018060200180602001838103835284818151815260200191508051906020019080838360005b838110156108155780820151818401526020810190506107fa565b50505050905090810190601f1680156108425780820380516001836020036101000a031916815260200191505b508381038252600c8152602001807f426174636852656d6f7665640000000000000000000000000000000000000000815250602001965050505050505060405180910390a25050565b610893610b94565b6000801515600160008a815260200190815260200160002060009054906101000a900460ff1615151415156108c757600080fd5b42905060a06040519081016040528089815260200188815260200187878080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505081526020018281526020018585808060200260200160405190810160405280939291908181526020018383602002808284378201915050505050508152509150816000808a815260200190815260200160002060008201518160000155602082015181600101556040820151816002019080519060200190610998929190610c2d565b506060820151816003015560808201518160040190805190602001906109bf929190610cad565b5090505060018060008a815260200190815260200160002060006101000a81548160ff021916908315150217905550877fcf3d2eef4f86b6891897e961560d385d7790d429d77dbf513cb828b2f8234ef582338a8a8a604051808681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018481526020018060200180602001838103835285858281815260200192508082843782019150508381038252600c8152602001807f426174636843726561746564000000000000000000000000000000000000000081525060200197505050505050505060405180910390a25050505050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610b0857803560ff1916838001178555610b36565b82800160010185558215610b36579182015b82811115610b35578235825591602001919060010190610b1a565b5b509050610b439190610cfa565b5090565b828054828255906000526020600020908101928215610b83579160200282015b82811115610b82578235825591602001919060010190610b67565b5b509050610b909190610cfa565b5090565b60a06040519081016040528060008152602001600081526020016060815260200160008152602001606081525090565b50805460018160011615610100020316600290046000825580601f10610bea5750610c09565b601f016020900490600052602060002090810190610c089190610cfa565b5b50565b5080546000825590600052602060002090810190610c2a9190610cfa565b50565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610c6e57805160ff1916838001178555610c9c565b82800160010185558215610c9c579182015b82811115610c9b578251825591602001919060010190610c80565b5b509050610ca99190610cfa565b5090565b828054828255906000526020600020908101928215610ce9579160200282015b82811115610ce8578251825591602001919060010190610ccd565b5b509050610cf69190610cfa565b5090565b610d1c91905b80821115610d18576000816000905550600101610d00565b5090565b905600a165627a7a7230582012a555d9b84a8b2fc1c4e8d154303bc85c801032adab342cedd7c3f20c4ef9010029";

    public static final String FUNC_CREATEBATCH = "createBatch";

    public static final String FUNC_UPDATEBATCH = "updateBatch";

    public static final String FUNC_GETBATCH = "getBatch";

    public static final String FUNC_DELETEBATCH = "deleteBatch";

    public static final Event BATCHCRUDEVENT_EVENT = new Event("BatchCRUDEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
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

    public List<BatchCRUDEventEventResponse> getBatchCRUDEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BATCHCRUDEVENT_EVENT, transactionReceipt);
        ArrayList<BatchCRUDEventEventResponse> responses = new ArrayList<BatchCRUDEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BatchCRUDEventEventResponse typedResponse = new BatchCRUDEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._batchId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._dateUpdated = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._from = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._quantity = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.description = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse._eventName = (String) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<BatchCRUDEventEventResponse> batchCRUDEventEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, BatchCRUDEventEventResponse>() {
            @Override
            public BatchCRUDEventEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BATCHCRUDEVENT_EVENT, log);
                BatchCRUDEventEventResponse typedResponse = new BatchCRUDEventEventResponse();
                typedResponse.log = log;
                typedResponse._batchId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._dateUpdated = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._from = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._quantity = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.description = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse._eventName = (String) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<BatchCRUDEventEventResponse> batchCRUDEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BATCHCRUDEVENT_EVENT));
        return batchCRUDEventEventObservable(filter);
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

    public static class BatchCRUDEventEventResponse {
        public Log log;

        public BigInteger _batchId;

        public BigInteger _dateUpdated;

        public String _from;

        public BigInteger _quantity;

        public String description;

        public String _eventName;
    }
}
