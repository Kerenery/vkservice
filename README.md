## Запуск

### Minikube

```bash
minikube start --memory 3G
```


### Bootstrap

```bash
kubectl apply -f kubernetes/boot/postgres
kubectl apply -f kubernetes/boot/rabbitmq
```

### Check pods

```bash
kubectl get po
```

### Create database appuser

```bash
kubectl exec -it postgres-0 -- psql -U kerenery -c "create database appuser;"
```

### Deploy

```bash
kubectl apply -f kubernetes/service/user
kubectl apply -f kubernetes/service/vkhandler
```
### Check pods one more time

```bash
kubectl get po
```
### Check logs of spring boot applications

```bash
kubectl logs -f <podName>
```
### Check services

```bash
kubectl get svc
```
### Get spring app url

```bash
minikube service <serviceName> --url
```
#### And finally, its alive!

https://www.youtube.com/watch?v=xos2MnVxe-c