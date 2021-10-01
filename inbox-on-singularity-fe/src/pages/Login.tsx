import { connect, ConnectedProps } from 'react-redux'
import React, { useReducer, useEffect } from 'react';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';

import TextField from '@material-ui/core/TextField';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import CardHeader from '@material-ui/core/CardHeader';
import Button from '@material-ui/core/Button';

const Login = (props: Props) => {


    useEffect(() => {
        if (state.username.trim() && state.password.trim()) {
            dispatch({
                type: 'setIsButtonDisabled',
                payload: false
            });
        } else {
            dispatch({
                type: 'setIsButtonDisabled',
                payload: true
            });
        }
    }, [state.username, state.password]);

    const handleLogin = () => {
        if (state.username === 'abc@email.com' && state.password === 'password') {
            dispatch({
                type: 'loginSuccess',
                payload: 'Login Successfully'
            });
        } else {
            dispatch({
                type: 'loginFailed',
                payload: 'Incorrect username or password'
            });
        }
    };

    const handleKeyPress = (event: React.KeyboardEvent) => {
        if (event.keyCode === 13 || event.which === 13) {
            state.isButtonDisabled || handleLogin();
        }
    };

    const handleUsernameChange: React.ChangeEventHandler<HTMLInputElement> =
        (event) => {
            dispatch({
                type: 'setUsername',
                payload: event.target.value
            });
        };

    const handlePasswordChange: React.ChangeEventHandler<HTMLInputElement> =
        (event) => {
            dispatch({
                type: 'setPassword',
                payload: event.target.value
            });
        }

    return (
        <form noValidate autoComplete="off">
            <Card>
                <CardHeader title="Login App" />
                <CardContent>
                    <div>
                        <TextField
                            error={state.isError}
                            fullWidth
                            id="username"
                            type="email"
                            label="Username"
                            placeholder="Username"
                            margin="normal"
                            onChange={handleUsernameChange}
                            onKeyPress={handleKeyPress}
                        />
                        <TextField
                            error={state.isError}
                            fullWidth
                            id="password"
                            type="password"
                            label="Password"
                            placeholder="Password"
                            margin="normal"
                            helperText={state.helperText}
                            onChange={handlePasswordChange}
                            onKeyPress={handleKeyPress}
                        />
                    </div>
                </CardContent>
                <CardActions>
                    <Button
                        variant="contained"
                        size="large"
                        color="secondary"
                        onClick={handleLogin}
                        disabled={state.isButtonDisabled}>
                        Login
            </Button>
                </CardActions>
            </Card>
        </form>
    );
}


interface RootState {
    messages: string
}



const mapState = (state: RootState) => ({
    messages: state.messages,
});


const mapDispatch = {
    login: (username: string, password: string) => ({
        type: 'LOGIN',
        payload: {
            username: username,
            password: password
        }
    })
}

const connector = connect(mapState, mapDispatch)

type PropsFromRedux = ConnectedProps<typeof connector>

type Props = PropsFromRedux & {
    backgroundColor: string
}

export default connect(mapState, mapDispatch)(Login);