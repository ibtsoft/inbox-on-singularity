import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { wsConnect } from './websocket';

const HOST = process.env.REACT_APP_WS_HOST;
const PREFIX = process.env.REACT_APP_PREFIX;

function HomeComponent({
  messages, dispatch
}){ 
  const host = `${PREFIX}://${HOST}/main`;

  useEffect(() => dispatch(wsConnect(host)), [dispatch, host]);
  
  const listItems = messages.items.map((d) => <li key={d.name}>{d.name}</li>);

  return (
    <div>
      <h1>Hello</h1>
      <ul>
        {listItems }
      </ul>
    </div>
  );
}

HomeComponent.propTypes = {
    messages: PropTypes.object,
    dispatch: PropTypes.func,
}

const s2p = (state, ownProps) => ({
    messages: state.messages,
  });
  
export default connect(s2p)(HomeComponent);