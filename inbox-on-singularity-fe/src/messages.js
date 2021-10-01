const messagesInitialState = { items: [] };

export const messagesReducer = (state = { ...messagesInitialState }, action) => {
  switch (action.type) {
    case 'UPDATE_MESSAGES':
      return { ...state, messages: action.data };
    default:
      return state;
  }
};
