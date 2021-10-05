const Client = require("socket.io-client");
const assert = require("chai").assert;

//const host = 'https://inbox.tykhonov.com';
const host = 'http://localhost:8080';

describe("pigeon socket.io API", () => {
    let clientSocket, clientSocketEx;

    before((done) => {
        clientSocket = new Client(host);

        clientSocket.on("connect_error", (err) => {
            console.log(`connect_error due to ${err.message}`);
        });

        clientSocket.on("connect", done);

        clientSocketEx = new Client(host);

        clientSocketEx.on("connect_error", (err) => {
            console.log(`connect_error due to ${err.message}`);
        });
    });

    after(() => {
        clientSocket.close();
        clientSocketEx.close();
    });

    let authToken;

    it("should login", (done) => {
        const loginMessage = {
            module: "AUTHENTICATION",
            type: "LOGIN",
            meta: {
                referenceId: 1
            },
            payload: {
                "username": "john",
                "password": "john-password"
            }
        };

        clientSocket.on("AUTHENTICATION", (arg) => {
            const response = JSON.parse(arg);
            assert.equal(response.status, "SUCCESS");
            assert.exists(response.token);
            authToken = response.token.token;
            done();
        });

        clientSocket.emit("AUTHENTICATION", JSON.stringify(loginMessage));
    });

    it("should login with token", (done) => {
        const loginMessage = {
            module: "AUTHENTICATION",
            type: "LOGIN_WITH_TOKEN",
            meta: {
                referenceId: 1
            },
            payload: {
                token: authToken
            }
        };

        clientSocketEx.on("AUTHENTICATION", (arg) => {
            const response = JSON.parse(arg);
            assert.equal(response.status, "SUCCESS");
            assert.exists(response.token);
            authToken = response.token.token;
            done();
        });

        clientSocketEx.emit("AUTHENTICATION", JSON.stringify(loginMessage));
    });

    it("should subscribe to mail", (done) => {
        const subscribeMessage = {
            module: "REPOSITORY",
            type: "SUBSCRIBE",
            meta: {
                referenceId: 2
            },
            payload: {
                repositories: ["Mail"]
            }
        }

        clientSocket.on("REPOSITORY", (arg) => {
            console.log(arg)
            const response = JSON.parse(arg);
            assert.equal(response.module, "REPOSITORY");
            assert.equal(response.type, "SYNC");
            assert.isAbove(response.payload.Mail.length, 1);
            done();
        });

        clientSocket.emit("REPOSITORY", JSON.stringify(subscribeMessage));
    });


    it("should send email", (done) => {
        const sendMailMessage = {
            module: "ACTION",
            type: "EXECUTE",
            meta: {
                referenceId: 3
            },
            payload: {
                name: "sendMail",
                params: {
                    fromUsername: "john",
                    toUsername: "jane",
                    subject: "Test subject",
                    body: "Test body"
                }
            }
        };

        clientSocket.removeAllListeners();

        let isSuccessReceived, isNewMailReceived;

        clientSocket.on("ACTION", (arg) => {
            const response = JSON.parse(arg);
            assert.equal(response.module, "ACTION");
            assert.equal(response.type, "EXECUTE_RESULT");
            assert.equal(response.status, "SUCCESS");
            isSuccessReceived = true;
            if (isSuccessReceived && isNewMailReceived) done();
        });

        clientSocket.on("REPOSITORY", (arg) => {
            const response = JSON.parse(arg);

            assert.equal(response.module, "REPOSITORY");
            assert.equal(response.type, "ADD");

            assert.equal(response.payload.Mail.value.subject, sendMailMessage.payload.params.subject);
            isNewMailReceived = true;
            if (isSuccessReceived && isNewMailReceived) done();
        });

        clientSocket.emit("ACTION", JSON.stringify(sendMailMessage));
    });

});