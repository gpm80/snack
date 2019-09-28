# ПереКус
## rest-интерфес
###Запросы по ТРЦ
**Возвращает ТРЦ в указанном радиусе**
`/trc/get/near/{lat}/{lon}/[{radius}]`

Примеры:

* [/trc/get/near/59.9339654/30.3032489](http://93.171.217.252/snack-server/trc/get/near/59.9339654/30.3032489)
* [/trc/get/near/59.9339654/30.3032489/500](http://93.171.217.252/snack-server/trc/get/near/59.9339654/30.3032489/500)


###Запросы по фудкортам
**Возвращает фудкорты в указанном радиусе**
`/food/get/near/{lat}/{lon}/[{radius}]`
Примеры:
* [/food/get/near/59.9339654/30.3032489](http://93.171.217.252/snack-server/food/get/near/59.9339654/30.3032489)

**Возвращает фудкорты в указанном ТРЦ**
`/food/get/in/trc/{trcId}`
Примеры:
* [/food/get/in/trc/{trcId}](93.171.217.252/snack-server/food/get/in/trc/db2cc8537b50020e8997659c7ccffcfa)
