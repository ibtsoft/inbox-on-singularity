import React from 'react';

import './App.css';

import { Provider} from 'react-redux'
import HomeComponent from './HomeComponent'
import store from './store';


function App() {  
  return (
      <Provider store={store}>
          <div className="App">
            <HomeComponent />
          </div>
      </Provider>
  )
}

export default App;