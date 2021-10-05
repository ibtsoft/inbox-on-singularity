
#GKE default timeout

```shell
kubectl describe ingress
```

```
Name:             inbox-ingress
Namespace:        default
Address:          35.241.5.67
Default backend:  inbox-service:80 (10.72.3.16:8080)
Rules:
  Host        Path  Backends
  ----        ----  --------
  *           *     inbox-service:80 (10.72.3.16:8080)
Annotations:  ingress.gcp.kubernetes.io/pre-shared-cert: mcrt-df115381-94bb-42ae-b808-fccaac33d792
              ingress.kubernetes.io/backends: {"k8s1-6aa205b0-default-inbox-service-80-c2a109b6":"HEALTHY"}
              ingress.kubernetes.io/forwarding-rule: k8s2-fr-jygrecw7-default-inbox-ingress-3adn09as
              ingress.kubernetes.io/https-forwarding-rule: k8s2-fs-jygrecw7-default-inbox-ingress-3adn09as
              ingress.kubernetes.io/https-target-proxy: k8s2-ts-jygrecw7-default-inbox-ingress-3adn09as
              ingress.kubernetes.io/ssl-cert: mcrt-df115381-94bb-42ae-b808-fccaac33d792
              ingress.kubernetes.io/target-proxy: k8s2-tp-jygrecw7-default-inbox-ingress-3adn09as
              ingress.kubernetes.io/url-map: k8s2-um-jygrecw7-default-inbox-ingress-3adn09as
              kubernetes.io/ingress.class: gce
              kubernetes.io/ingress.global-static-ip-name: inbox-external-ip
              networking.gke.io/managed-certificates: inbox-managed-cert
Events:
  Type    Reason  Age                   From                     Message
  ----    ------  ----                  ----                     -------
  Normal  Sync    8m11s (x35 over 21h)  loadbalancer-controller  Scheduled for sync

```

Look for ```ingress.kubernetes.io/backends```. Run:

```shell
gcloud compute backend-services update k8s1-6aa205b0-default-inbox-service-80-c2a109b6 --global --timeout=86400
```

See https://github.com/kubernetes/ingress-gce/tree/b0603c69382a39d097063eab8e3d9e30ca1cdf7b/examples/websocket