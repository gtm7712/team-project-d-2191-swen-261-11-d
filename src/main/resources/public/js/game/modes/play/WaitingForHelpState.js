/**
 * This module exports the WaitingForHelpState class constructor.
 * 
 * This component is an concrete implementation of a state
 * for the Game view; this state represents the view state
 * in which the player has requested for help.
 */
define(function(require){
    'use strict';
    
    // imports
    const PlayModeConstants = require('./PlayModeConstants');
    const AjaxUtils = require('../../util/AjaxUtils');
  
    /**
     * Constructor function.
     * 
     * @param {PlayController} controller
     *    The Play mode controller object.
     */
    function WaitingForHelpState(controller) {
      // private attributes
      this._controller = controller;
    }
  
    //
    // Public (external) methods
    //
  
    /**
     * Get the name of this state.
     */
    WaitingForHelpState.prototype.getName = function getName() {
      return PlayModeConstants.PLAYER_HELP;
    };
    
    /**
     * Hook when entering this state.
     */
    WaitingForHelpState.prototype.onEntry = function onEntry() {
  
      // 1) disable UI controls
      this._controller.disableButton(PlayModeConstants.BACKUP_BUTTON_ID);
      this._controller.disableButton(PlayModeConstants.SUBMIT_BUTTON_ID);
      this._controller.disableButton(PlayModeConstants.RESIGN_BUTTON_ID);
      this._controller.disableButton(PlayModeConstants.HELP_BUTTON_ID);
      
      // 2) disable all Pieces
      this._controller.disableAllMyPieces();
      
      // 3) ask the server to validate the pending move
      AjaxUtils.callServer(
          // the action takes a single move
          '/playerHelp',
          // the handler method should be run in the context of 'this' State object
          handleResponse, this);
    };
  
    //
    // Private methods
    //
  
    function handleResponse(message) {
      if (message.type === 'ERROR') {
          
      }
      else {
        this._controller.highlight(message.text.split(" "));
        this._controller.setState(PlayModeConstants.EMPTY_TURN);
      }
      message.text = "The spaces of the moveable pieces are highlighted!"
      this._controller.displayMessage(message);
    }
  
    // export class constructor
    return WaitingForHelpState;
    
  });
  