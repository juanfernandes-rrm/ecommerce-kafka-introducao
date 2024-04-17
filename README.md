# Projeto

Este projeto foi desenvolvido durante o curso [Kafka: produtores, consumidores e streams](https://cursos.alura.com.br/course/kafka-introducao-a-streams-em-microservicos) da Alura.
O curso utilizou o contexto de um sistema ecommerce para aplicar os conceitos produção e consumo de mensagens, explicando o funcionamento de tópicos, grupos e partições.
Abaixo esta as minhas anotações.

# Mensageria

A mensageria permite a comunicação assíncrona e paralela entre os diferentes serviços de um sistema.


# Kafka e ZooKeeper

O Kafka permite a distribuição de mensagens entre várias instâncias de um serviço, o que possibilita o paralelismo, a escalabilidade e a tolerância a falhas.

O ZooKeeper é um serviço centralizado para manter informações de configuração, nomeação, sincronização e grupos de processos distribuídos. Ele é usado pelo Kafka para gerenciar e coordenar todos os brokers (servidores Kafka) em um cluster.

## Comandos

- Iniciar o ZooKeeper


```
./bin/zookeeper-server-start.sh ./config/zookeeper.properties
```

- Iniciar o Kafka

```
./bin/kafka-server-start.sh ./config/server.properties
```

- Criar um tópico

```
./bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic LOJA_NOVO_PEDIDO
```

- Listar tópicos criados

```
./bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

- Criar um produtor

```
./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic LOJA_NOVO_PEDIDO
```

- Consumir mensagens
  
```
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO --from-beginning
```

- Alterar número de partições

```
./bin/kafka-topics.sh --alter --bootstrap-server localhost:9092 --topic ECOMMERCE_NEW_ORDER --partitions 3
```

- Descrição de um grupo

```
./bin/kafka-consumer-groups.sh --all-groups --bootstrap-server localhost:9092 --describe
```

# Grupos

Grupos, no contexto de Kafka, referem-se a um conjunto de consumidores que compartilham a mesma lógica de consumo de mensagens de um tópico específico. Dentro de um grupo, uma mensagem é consumida por apenas um consumidor. Isso permite o paralelismo.

```
properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getSimpleName());
```

Entretanto, é necessário configurar o Kafka para que as mensagens sejam distribuídas entre os consumidores do grupo. Caso contrário, apenas um consumidor recebe as mensagens. Essa configuração é a partição.


# Partições

Uma partição é uma subdivisão do tópico e funciona como uma lista de mensagens. O tópico recebe as mensagens em partições, e cada consumidor se responsabiliza por uma partição. Portanto, não faz sentido ter mais consumidores do que o número de partições.

Além disso, o Kafka distribui as mensagens com base nas chaves. Ou seja, as partições existentes recebem mensagens com base na chave. Dessa forma, se uma mensagem for enviada com a mesma chave, ela sempre será enviada para a mesma partição, impossibilitando o paralelismo.

# Rebalanceamento de partições

O Kafka pode decidir redistribuir as partições entre os consumidores por vários motivos. No entanto, esse processo pode afetar o paralelismo.

Durante o consumo de mensagens, pode ocorrer o rebalanceamento das partições. Quando isso acontece, as mensagens processadas por um consumidor cuja partição foi rebalanceada não podem ser confirmadas como processadas (commit) e precisam ser processadas novamente pelo novo consumidor responsável pela partição.

Para contornar esse problema, podemos configurar a propriedade MAX_POLL_RECORDS_CONFIG para definir o número máximo de mensagens a serem processadas por commit.

```
properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
```
