# Обзор 

|[English](https://github.com/sirenexcelsior/SpecialCourse/blob/main/Introduction%20to%20algorithms%20and%20data%20structures/Readme.md)|[Russina](https://github.com/sirenexcelsior/SpecialCourse/blob/main/Introduction%20to%20algorithms%20and%20data%20structures/READMERU.md)|

В этом разделе будут представлены все решения финального задания спецкурса ["Введение в алгоритмы и структуры данных"](https://scs.math.msu.ru/node/3762). 

О заданиях 

Предлагается 7 заданий. Итоговая оценка равна количеству сделанных за- даний.

Задания должны быть сделаны на языках C# или Java. При желании можно использовать Kotlin на JVM. Язык выбираете сами. Для написания программ рекомендуется использовать следующие среды рзработки (IDE):

- Microsoft Visual Studio (на Windows для C#); • Rider (на Linux/MacOS для C#);
- Intellij IDEA (для Java или Kotlin).

При создании нового проекта Java в Intellij IDEA рекомендуется исполь- зовать систему сборки Maven.

Настоятельная просьба сделать задания в Git проекте на GitHub или GitFlic. Сделайте приватный проект и поделитесь с с пользователем @einsamhauer.

Очень рекомендуется сделать к своим заданиям тесты. Как это делается можно посмотреть в примерах:

- https://github.com/mmalgo/Stack для C#
- https://github.com/mmalgo/IntStack для Java

В обоих проектах для примера реализован минимальный стек с неболь- шим количеством тестов. (Для Java это стек для int.)

# Решения и Задания 

## 1 Бинарный поиск 

Реализуйте обобщенный метод для бинарного поиска в массиве, используя `IComparer`/`Comparer`. 

Реализация может выглядеть так:



```csharp
public class BinarySearch
{
    public static T Find<T>(T[] array, IComparer<T> comparer, T element)
    {
        ...
    }
}
```

для C#. Или так для Java:

```java
public class BinarySearch {
    public static <T> T find(T[] array, Comparator<T> comparator, T element) {
        ...
    }
}
```

