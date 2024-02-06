#!/bin/bash

ssh root@192.168.1.218 'rm -rf /home/kafka/logs && systemctl restart kafka'
ssh root@192.168.1.22 'rm -rf /home/kafka/logs && systemctl restart kafka'
ssh root@192.168.1.69 'rm -rf /home/kafka/logs && systemctl restart kafka'