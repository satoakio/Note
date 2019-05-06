## 归并排序(MergeSort)

与QuickSort一样，归并排序（Merge Sort）也是一种分治法（Divide and Conquer）的非常典型的应用。将已有序的子序列合并，得到完全有序的序列；`即先使每个子序列有序，再使子序列之间有序。将两个有序数组合并成一个有序数组，`称为二路归并(binary merge)。

### 核心思想

将一个数组拆分成两半，分别对每一半进行排序，然后使用合并(merge)操作，把两个有序的子数组合并成一个整体的有序数组。我们可以把一个数组刚开始先分成两，也就是2个1/2，之后再将每一半分成两半，也就是4个1/4，以此类推，反复的分隔数组，直到得到的子数组中只包含一个数据项，这就是基值条件(也就是递归终止条件)，只有一个数据项的子数组肯定是有序的。

归并算法的核心是归并两个已经有序的数组。归并两个有序数组A和B，就生成了第三个数组C，数组C包含数组A和B的所有数据项，并且使它们有序的排列在数组C中。

### 时间复杂度

之前讲解的冒泡排序，选择排序，插入排序都是时间复杂度都是O(n2)，而归并排序只需要O(n*logn)，效率比前三种算法提升很多。

空间复杂度为 O(n).归并排序比较占用内存，但却是一种效率高且稳定的算法。

### 归并排序和冒泡排序比较

    冒泡排序和归并排序运行的时间比较,x轴为需要排序的元素的个数，y轴为排好序需要花费的时间.
    可以看出，虽然归并排序的运行时间小于冒泡排序，但是在排序数字数目较少时，
    冒泡排序的性能要优于归并排序，只是随着元素个数的增加，冒泡排序的时间增加的
    比归并排序快.由此得出以下归并排序的升级版：
    设置一个阈值min，当需要排序的数字数目大于min时，继续使用归并排序；
    反之，当需要排序的数字数目小于min时，进行冒泡排序。