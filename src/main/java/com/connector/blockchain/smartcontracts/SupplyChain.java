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
    private static final String BINARY = "0x608060405234801561001057600080fd5b50610ec0806100206000396000f3fe608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680634f8fed21146100675780635ac4428214610156578063d06b1da814610267578063d439fd8a146102a2575b600080fd5b34801561007357600080fd5b506101546004803603608081101561008a57600080fd5b810190808035906020019092919080359060200190929190803590602001906401000000008111156100bb57600080fd5b8201836020820111156100cd57600080fd5b803590602001918460018302840111640100000000831117156100ef57600080fd5b90919293919293908035906020019064010000000081111561011057600080fd5b82018360208201111561012257600080fd5b8035906020019184602083028401116401000000008311171561014457600080fd5b9091929391929390505050610391565b005b34801561016257600080fd5b5061018f6004803603602081101561017957600080fd5b8101908080359060200190929190505050610511565b604051808681526020018060200185815260200184815260200180602001838103835287818151815260200191508051906020019080838360005b838110156101e55780820151818401526020810190506101ca565b50505050905090810190601f1680156102125780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019060200280838360005b8381101561024e578082015181840152602081019050610233565b5050505090500197505050505050505060405180910390f35b34801561027357600080fd5b506102a06004803603602081101561028a57600080fd5b81019080803590602001909291905050506106c0565b005b3480156102ae57600080fd5b5061038f600480360360808110156102c557600080fd5b810190808035906020019092919080359060200190929190803590602001906401000000008111156102f657600080fd5b82018360208201111561030857600080fd5b8035906020019184600183028401116401000000008311171561032a57600080fd5b90919293919293908035906020019064010000000081111561034b57600080fd5b82018360208201111561035d57600080fd5b8035906020019184602083028401116401000000008311171561037f57600080fd5b90919293919293905050506109c9565b005b600115156001600088815260200190815260200160002060009054906101000a900460ff1615151415156103c457600080fd5b846000808881526020019081526020016000206001018190555083836000808981526020019081526020016000206002019190610402929190610c3c565b5081816000808981526020019081526020016000206004019190610427929190610cbc565b50857f36dcc9ed9e941265c6d92b2912168bc4e15dddc7bf9a696e01255fbe143b97864233888888604051808681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200184815260200180602001806020018381038352858582818152602001925080828437600081840152601f19601f8201169050808301925050508381038252600c8152602001807f426174636855706461746564000000000000000000000000000000000000000081525060200197505050505050505060405180910390a2505050505050565b600060606000806060600115156001600088815260200190815260200160002060009054906101000a900460ff16151514151561054d57600080fd5b610555610d09565b60008088815260200190815260200160002060a060405190810160405290816000820154815260200160018201548152602001600282018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106205780601f106105f557610100808354040283529160200191610620565b820191906000526020600020905b81548152906001019060200180831161060357829003601f168201915b50505050508152602001600382015481526020016004820180548060200260200160405190810160405280929190818152602001828054801561068257602002820191906000526020600020905b81548152602001906001019080831161066e575b505050505081525050905080600001518160400151826020015183606001518460800151839350809050955095509550955095505091939590929450565b600115156001600083815260200190815260200160002060009054906101000a900460ff1615151415156106f357600080fd5b6106fb610d09565b60008083815260200190815260200160002060a060405190810160405290816000820154815260200160018201548152602001600282018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107c65780601f1061079b576101008083540402835291602001916107c6565b820191906000526020600020905b8154815290600101906020018083116107a957829003601f168201915b50505050508152602001600382015481526020016004820180548060200260200160405190810160405280929190818152602001828054801561082857602002820191906000526020600020905b815481526020019060010190808311610814575b505050505081525050905060008083815260200190815260200160002060008082016000905560018201600090556002820160006108669190610d39565b600382016000905560048201600061087e9190610d81565b50506001600083815260200190815260200160002060006101000a81549060ff0219169055817f36dcc9ed9e941265c6d92b2912168bc4e15dddc7bf9a696e01255fbe143b9786423384602001518560400151604051808581526020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018381526020018060200180602001838103835284818151815260200191508051906020019080838360005b83811015610953578082015181840152602081019050610938565b50505050905090810190601f1680156109805780820380516001836020036101000a031916815260200191505b508381038252600c8152602001807f426174636852656d6f7665640000000000000000000000000000000000000000815250602001965050505050505060405180910390a25050565b600015156001600088815260200190815260200160002060009054906101000a900460ff1615151415156109fc57600080fd5b610a04610d09565b600042905060a06040519081016040528089815260200188815260200187878080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050508152602001828152602001858580806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f820116905080830192505050505050508152509150816000808a815260200190815260200160002060008201518160000155602082015181600101556040820151816002019080519060200190610afb929190610da2565b50606082015181600301556080820151816004019080519060200190610b22929190610e22565b5090505060018060008a815260200190815260200160002060006101000a81548160ff021916908315150217905550877f36dcc9ed9e941265c6d92b2912168bc4e15dddc7bf9a696e01255fbe143b978682338a8a8a604051808681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200184815260200180602001806020018381038352858582818152602001925080828437600081840152601f19601f8201169050808301925050508381038252600c8152602001807f426174636843726561746564000000000000000000000000000000000000000081525060200197505050505050505060405180910390a25050505050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610c7d57803560ff1916838001178555610cab565b82800160010185558215610cab579182015b82811115610caa578235825591602001919060010190610c8f565b5b509050610cb89190610e6f565b5090565b828054828255906000526020600020908101928215610cf8579160200282015b82811115610cf7578235825591602001919060010190610cdc565b5b509050610d059190610e6f565b5090565b60a06040519081016040528060008152602001600081526020016060815260200160008152602001606081525090565b50805460018160011615610100020316600290046000825580601f10610d5f5750610d7e565b601f016020900490600052602060002090810190610d7d9190610e6f565b5b50565b5080546000825590600052602060002090810190610d9f9190610e6f565b50565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610de357805160ff1916838001178555610e11565b82800160010185558215610e11579182015b82811115610e10578251825591602001919060010190610df5565b5b509050610e1e9190610e6f565b5090565b828054828255906000526020600020908101928215610e5e579160200282015b82811115610e5d578251825591602001919060010190610e42565b5b509050610e6b9190610e6f565b5090565b610e9191905b80821115610e8d576000816000905550600101610e75565b5090565b9056fea165627a7a723058209c3e16fc068be8a37d5bca3b079814fda296d3816da6cc3832a96d6f74ced0120029";

    public static final String FUNC_CREATEBATCH = "createBatch";

    public static final String FUNC_UPDATEBATCH = "updateBatch";

    public static final String FUNC_GETBATCH = "getBatch";

    public static final String FUNC_DELETEBATCH = "deleteBatch";

    public static final Event BATCHCRUD_EVENT = new Event("BatchCRUD", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("5777", "0x839Fd8D5eA289eC6A25C4B66f4c490da198c8a18");
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

    public List<BatchCRUDEventResponse> getBatchCRUDEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BATCHCRUD_EVENT, transactionReceipt);
        ArrayList<BatchCRUDEventResponse> responses = new ArrayList<BatchCRUDEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BatchCRUDEventResponse typedResponse = new BatchCRUDEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._batchId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._date = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._from = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._quantity = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._description = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse._eventName = (String) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<BatchCRUDEventResponse> batchCRUDEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, BatchCRUDEventResponse>() {
            @Override
            public BatchCRUDEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BATCHCRUD_EVENT, log);
                BatchCRUDEventResponse typedResponse = new BatchCRUDEventResponse();
                typedResponse.log = log;
                typedResponse._batchId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._date = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._from = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._quantity = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._description = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse._eventName = (String) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<BatchCRUDEventResponse> batchCRUDEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BATCHCRUD_EVENT));
        return batchCRUDEventObservable(filter);
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

    public static class BatchCRUDEventResponse {
        public Log log;

        public BigInteger _batchId;

        public BigInteger _date;

        public String _from;

        public BigInteger _quantity;

        public String _description;

        public String _eventName;
    }
}
