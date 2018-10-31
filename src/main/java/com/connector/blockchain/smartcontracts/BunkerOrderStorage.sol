pragma solidity ^0.4.23;

contract BunkerOrderStorage {

  event OrderUpdated(uint indexed orderId, string supplyingOrg, string receivingOrg, string testResults);

  struct BunkerOrder {
    uint orderId;
    string supplyingOrg;
    string receivingOrg;
    string testResults;
  }

  mapping (uint => BunkerOrder) orderToBunker;
  mapping (uint => bool) orderIdExist;

  function createBunkerOrder(uint _orderId, string _supplyingOrg, string _receivingOrg, string _testResults) external {
    require(orderIdExist[_orderId] == false);
    BunkerOrder memory order = BunkerOrder(_orderId, _supplyingOrg, _receivingOrg, _testResults);
    orderToBunker[_orderId] = order;
    orderIdExist[_orderId] = true;
    emit OrderUpdated(_orderId, _supplyingOrg, _receivingOrg, _testResults);
  }

  function updateBunkerOrder(uint _orderId, string _supplyingOrg, string _receivingOrg, string _testResults) external {
    require(orderIdExist[_orderId] == true);
    orderToBunker[_orderId].supplyingOrg = _supplyingOrg;
    orderToBunker[_orderId].receivingOrg = _receivingOrg;
    orderToBunker[_orderId].testResults = _testResults;
    emit OrderUpdated(_orderId, _supplyingOrg, _receivingOrg, _testResults);
  }

  function getBunkerOrder(uint _orderId) external view returns(string, string, string) {
    require(orderIdExist[_orderId] == true);
    return (orderToBunker[_orderId].supplyingOrg ,orderToBunker[_orderId].receivingOrg, orderToBunker[_orderId].testResults);
  }
}
