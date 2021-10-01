import { combineReducers } from 'redux';
import { messagesReducer } from './messages';
import { websocketReducer } from './websocket';

const rootReducer = combineReducers({
    websocket: websocketReducer,
    messages: messagesReducer
});

export default rootReducer;