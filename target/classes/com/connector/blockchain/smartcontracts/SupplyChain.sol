pragma solidity ^0.4.23;

contract SupplyChain {

  event BatchCRUDEvent(uint indexed _batchId, uint _dateUpdated, address _from, uint _quantity, string description, string _eventName);

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
    emit BatchCRUDEvent(_batchId, time, msg.sender, _quantity, _description, "BatchCreated");
  }

  function updateBatch(uint _batchId, uint _quantity, string _description, uint[] _sourceBatchIds) external {
    require(batchIdExist[_batchId] == true);
    batchIdToBatch[_batchId].quantity = _quantity;
    batchIdToBatch[_batchId].description = _description;
    batchIdToBatch[_batchId].sourceBatchIds = _sourceBatchIds;
    emit BatchCRUDEvent(_batchId, now, msg.sender, _quantity, _description, "BatchUpdated");
  }

  function getBatch(uint _batchId) external view returns(uint, string, uint, uint, uint[]) {
    require(batchIdExist[_batchId] == true);
    Batch memory batch = batchIdToBatch[_batchId];
    return (batch.batchId, batch.description, batch.quantity, batch.dateCreated, batch.sourceBatchIds);
  }

  function deleteBatch(uint _batchId) external {
    require(batchIdExist[_batchId] == true);
    Batch memory batch = batchIdToBatch[_batchId];
    delete(batchIdToBatch[_batchId]);
    delete(batchIdExist[_batchId]);
    emit BatchCRUDEvent(_batchId, now, msg.sender, batch.quantity, batch.description, "BatchRemoved");
  }
}
