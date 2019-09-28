# ПереКус
## rest-интерфес
### Запросы по ТРЦ
**Возвращает ТРЦ в указанном радиусе**
`/trc/get/near/{lat}/{lon}/[{radius}]`

Примеры:
* [/trc/get/near/59.9339654/30.3032489](http://93.171.217.252/snack-server/trc/get/near/59.9339654/30.3032489)
* [/trc/get/near/59.9339654/30.3032489/500](http://93.171.217.252/snack-server/trc/get/near/59.9339654/30.3032489/500)


### Запросы по фудкортам
**Возвращает фудкорты в указанном радиусе**
`/food/get/near/{lat}/{lon}/[{radius}]`

Примеры:
* [/food/get/near/59.9339654/30.3032489](http://93.171.217.252/snack-server/food/get/near/59.9339654/30.3032489)

**Возвращает фудкорты в указанном ТРЦ**
`/food/get/in/trc/{trcId}`

+ Примеры:
* [/food/get/in/trc/{trcId}](93.171.217.252/snack-server/food/get/in/trc/db2cc8537b50020e8997659c7ccffcfa)

### Запросы наборов блюд и меню
**Возвращает наборы в указанном кафе**
`/group/get/by/food/{foodId}`

+ Примеры:
* [/group/get/by/food/{foodId}](93.171.217.252/snack-server/group/get/by/food/37e50c2150528f22b2bc5e310a008a9f)

**Возвращает меню в указанном кафе**
`/menu/get/by/food/{foodId}`

+ Примеры:
* [/menu/get/by/food/{foodId}](93.171.217.252/snack-server/menu/get/by/food/37e50c2150528f22b2bc5e310a008a9f)
