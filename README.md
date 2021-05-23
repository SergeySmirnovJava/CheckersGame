# Checkers game
Консольное приложение для валидации ходов в русских шашках
# Описание задачи 
На вход программы может поступать композиции начала или середины партии русских шашек. Затем следует список из одного или нескольких ходов. На выходе требуется получить итоговую композицию шашек или сообщение об ошибке если какой то их ходов списка не невозможен.

![image](https://user-images.githubusercontent.com/78054096/119276279-a2225100-bc22-11eb-8317-65e8cfc0ca5b.png) ![image](https://user-images.githubusercontent.com/78054096/119276427-dac22a80-bc22-11eb-8bc6-552014255fac.png)

# Формат входных данных 
Координаты шашек и ходоов записываются в стандартной шашечной нотации. Характеристики ходов (! - хороший ход, ? - плохой хот и т.д.) при записи не применяются. Если шашка является дамкой, то ее кордината обозначается с большой буквы (D4 и т.д.). Если за один ход происходит нескольких взятий, то они будут записываться через ":"
Входные данные:
1. Строка с координатами белых шашек.
2. Строка с координатами черных шашек.
3. Список ходов.Пара ходов (черные + белые) в строке.
# Типы сообщений об ошибках
Все исходные позиции шашек являются валидными.
 - **busy cell** - целевая клетка занята.
 - **white cell** - целевая клетка - белая (шашки могут расставляться только на черные клетки, и в силу правил не могут оказаться на белых клетках).
 - **invalid move** - в шашках бить обязательно. причем, всю цепь до конца. Ошибка выводится, если игрок мог побить шашку врага, но он его не использует, а идет на другую клетку. Если вариантов несколько, можно взять любой.
 - **error** - другие ошибки
