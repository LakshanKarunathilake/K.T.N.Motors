/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

/**
 *
 * @author lakshan
 */
public class ItemSearch {
    private ItemSearch itemSearch;
    
    private ItemSearch(){
        
    }
    
    public void getInstance(){
        if(itemSearch){
            itemSearch = new ItemSearch();
        }
        retur itemSearch;
    }
    
    public ArrayList getSearchResults(String value){
        
    }
    
}
