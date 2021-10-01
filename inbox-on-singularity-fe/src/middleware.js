import * as actions from './websocket';
import { updateMessages } from './actions';

const socketMiddleware = () => {
    let socket = null;

    const onOpen = store => (event) => {
        store.dispatch(actions.wsConnected(event.target.url));
    };

    const onClose = store => () => {
        store.dispatch(actions.wsDisconnected());
    };

    const onMessage = store => (event) => {
        const payload = JSON.parse(event.data);
        switch (payload.type) {
            case 'update_messages':
                console.log(payload);
                store.dispatch(updateMessages(payload.messages));
                break;
            default:
                console.log(payload);
                break;
        }
    };

    return store => next => (action) => {
        switch (action.type) {
            case 'WS_CONNECT':
                if (socket !== null) {
                    socket.close();
                }
                console.log(action.host);
                // connect to the remote host
                socket = new WebSocket(action.host);

                // websocket handlers
                socket.onmessage = onMessage(store);
                socket.onclose = onClose(store);
                socket.onopen = onOpen(store);

                break;
            case 'WS_DISCONNECT':
                if (socket !== null) {
                    socket.close();
                }
                socket = null;
                break;
            case 'LOGIN':
                socket.send(JSON.stringify({ action: 'LOGIN', data: { username: action.username, password: action.password } }));
                break;
            case 'REPOSITORY_ADD':
                socket.send(JSON.stringify({ action: 'REPOSITORY_ADD', data: { repository: action.repository, object: action.object } }));
                break;
            default:
                return next(action);
        }
    };
};

export default socketMiddleware();