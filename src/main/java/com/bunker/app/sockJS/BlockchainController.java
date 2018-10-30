package com.bunker.app.sockJS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import com.bunker.app.blockchain.BlockchainConnection;

@EnableScheduling
@Controller
public class BlockchainController {

	private String oldBlkNum = "";
	
    @Autowired
    private SimpMessagingTemplate template;
    
//  private final Logger log = LoggerFactory.getLogger(BlockchainController.class);

//  ** CHECK FOR NEW BLOCKS AND SENT TO CLIENT VIA WEBSOCKET **
    @Scheduled(fixedRate = 5000)
    public void sendBlockNumber() throws Exception {
    	String newBlkNum = BlockchainConnection.getBlockNumber();
    	if(newBlkNum.equals("")) {
    		return;
    	}
    	if(newBlkNum.equals(oldBlkNum)){
    		return;
    	}
        oldBlkNum = newBlkNum;
        template.convertAndSend("/topic/blocknumber", newBlkNum);
    }
}