# Overview

|[English](https://github.com/sirenexcelsior/SpecialCourse/blob/main/Introduction%20to%20algorithms%20and%20data%20structures/READMEEN.md)|[Русский](https://github.com/sirenexcelsior/SpecialCourse/blob/main/Introduction%20to%20algorithms%20and%20data%20structures/README.md)|

In this section will be included all solutions to the final task of the special course ["Введение в алгоритмы и структуры данных"](https://scs.math.msu.ru/node/3762).

About the tasks

There are 7 assignments. The final grade is equal to the number of assignments done.
The assignments should be done in C# or Java. If you wish, you can use Kotlin on JVM. You can choose the language yourself. The following IDEs are recommended for writing programmes:

- Microsoft Visual Studio (on Windows for C#); 

- Rider (on Linux/MacOS for C#);

- Intellij IDEA (for Java or Kotlin).

When creating a new Java project in Intellij IDEA, it is recommended to use the Maven build system.
It is strongly recommended to make tasks in a Git project on GitHub or GitFlic. Make a private project and share it with the user @einsamhauer.
It is highly recommended to make tests for your assignments. You can see how to do it in the examples:

- https://github.com/mmalgo/Stack for C#

- https://github.com/mmalgo/IntStack for Java

In both projects a minimal stack with a small number of tests is implemented as an example. (For Java it is a stack for int.)

# Solutions and Tasks

## 1 Binary Search

Implement a generic method for binary search in an array using `IComparer`/`Comparer`.

The implementation might look like this:

```csharp
public class BinarySearch
{
    public static T Find<T>(T[] array, IComparer<T> comparer, T element)
    {
        ...
    }
}
```
for C#. Or like this for Java:
```java
public class BinarySearch {
    public static <T> T find(T[] array, Comparator<T> comparator, T element) {
        ...
    }
}
```
