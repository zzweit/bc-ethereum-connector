pragma solidity ^0.4.23;

contract SupplyChain {

  event BatchUpdated(uint indexed _batchId, uint _dateUpdated, address _from, string state);
  event BatchRemoved(uint indexed _batchId, uint _dateRemoved, address _from);

  struct Batch {
    uint batchId;
    uint quantity;
    string description;
    uint dateCreated;
    uint[] sourceBatchIds;
  }

  mapping (uint => Batch) batchIdToBatch; //To track which batch details with BatchId
  mapping (uint => bool) batchIdExist; //To track if batch ID exist
  // mapping (uint => bool) companyIdExist; //To track if company Id  existed to prevent any clashing


  function createBatch(uint _batchId, uint _quantity, string _description, uint[] _sourceBatchIds) external {
    require(batchIdExist[_batchId] == false);
    Batch memory batch;
    uint time = now;
    batch = Batch(_batchId, _quantity, _description, time, _sourceBatchIds);
    batchIdToBatch[_batchId] = batch;
    batchIdExist[_batchId] = true; 
    string memory state = string(abi.encodePacked("Batch ", _batchId, " has been created at ", time));
    emit BatchUpdated(_batchId, time, msg.sender, state);
  }

  function updateBatch(uint _batchId, uint _quantity, string _description, uint[] _sourceBatchIds) external {
    require(batchIdExist[_batchId] == true);
    uint time = now;
    batchIdToBatch[_batchId].quantity = _quantity;
    batchIdToBatch[_batchId].description = _description;
    batchIdToBatch[_batchId].sourceBatchIds = _sourceBatchIds;

    string memory state = string(abi.encodePacked("Batch ", _batchId, " has been updated at ", time));
    emit BatchUpdated(_batchId, time, msg.sender, state);
  }

  function getBatch(uint _batchId) external view returns(uint, string, uint, uint, uint[]) {
    require(batchIdExist[_batchId] == true);
    Batch memory batch = batchIdToBatch[_batchId];
    return (batch.batchId, batch.description, batch.quantity, batch.dateCreated, batch.sourceBatchIds);
  }

  function deleteBatch(uint _batchId) external {
    require(batchIdExist[_batchId] == true);
    delete(batchIdToBatch[_batchId]);
    delete(batchIdExist[_batchId]);
    emit BatchRemoved(_batchId, now, msg.sender);
  }
}
