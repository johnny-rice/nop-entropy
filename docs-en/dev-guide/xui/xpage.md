# Frontend Configuration

## 1. input-tree默认设置了最大高度 max-height:300px

```javascript
{
  type: 'input-tree',
  treeContainerClassName: 'h-full'
}
```

`h-full` 可以用于去除 `max-height` 的设置。

## 2. 设置 page 的 aside 区域的宽度

```javascript
{
  type: 'page',
  aside: [{
    type : 'input-text'
  }],
  asideResizor: true,
  asideClassName: 'w-60'
}
```

可配置的宽度类参见 amis-ui/helper/sizing/_width.scss

|Class       |Properties              |
|------------|-------------------------|
|w-px        |width: 0.0625rem;      |
|w-0         |width: 0;               |
|w-none      |width: 0;               |
|w-0.5       |width: 0.125rem;       |
|w-1         |width: 0.25rem;        |
|w-1.5       |width: 0.375rem;       |
|w-2         |width: 0.5rem;         |
|w-2.5       |width: 0.625rem;       |
|w-3         |width: 0.75rem;        |
|w-3.5       |width: 0.875rem;       |
|w-4         |width: 1rem;           |
|w-5         |width: 1.25rem;        |
|w-6         |width: 1.5rem;         |
|w-7         |width: 1.75rem;        |
|w-8         |width: 2rem;           |
|w-9         |width: 2.25rem;        |
|w-10        |width: 2.5rem;         |
|w-11        |width: 2.75rem;        |
|w-12        |width: 3rem;           |
|w-14        |width: 3.5rem;         |
|w-16        |width: 4rem;           |
|w-18        |width: 4.5rem;         |
|w-20        |width: 5rem;           |
|w-24        |width: 6rem;           |
|w-28        |width: 7rem;           |
|w-32        |width: 8rem;           |
|w-36        |width: 9rem;           |
|w-40        |width: 10rem;          |
|w-44        |width: 11rem;          |
|w-48        |width: 12rem;          |
|w-52        |width: 13rem;          |
|w-56        |width: 14rem;          |
|w-60        |width: 15rem;          |
|w-64        |width: 16rem;          |
|w-72        |width: 18rem;          |
|w-80        |width: 20rem;          |
|w-96        |width: 24rem;          |
|w-auto      |width: auto;           |
|w-1x        |width: 1em;            |
|w-2x        |width: 2em;            |
|w-3x        |width: 3em;            |
|w-1/2       |width: 50%;             |
|w-1/3       |width: 33.333333%;     |
|w-2/3       |width: 66.666667%;    |
|w-1/4       |width: 25%;             |
|w-2/4       |width: 50%;             |
|w-3/4       |width: 75%;             |
|w-1/5       |width: 20%;             |
|w-2/5       |width: 40%;             |
|w-3/5       |width: 60%;             |
|w-4/5       |width: 80%;             |
|w-1/6       |width: 16.666667%;     |
|w-2/6       |width: 33.333333%;     |
|w-3/6       |width: 50%;             |
|w-4/6       |width: 66.666667%;    |
|w-5/6       |width: 83.333333%;     |
|w-1/12      |width: 8.333333%;     |
|w-2/12      |width: 16.666667%;     |
|w-3/12      |width: 25%;             |
|w-4/12      |width: 33.333333%;     |
|w-5/12      |width: 41.666667%;     |
|w-6/12      |width: 50%;             |
|w-7/12      |width: 58.333333%;     |
|w-8/12      |width: 66.666667%;    |
|w-9/12      |width: 75%;             |
|w-10/12     |width: 83.333333%;     |
|w-11/12     |width: 91.666667%;     |
|w-full       |width: 100%;            |
|w-screen     |width: 100vw;           |
|w-min        |width: min-content;      |
|w-max        |width: max-content;      |
|min-w-0     |min-width: 0px;         |
|min-w-full   |min-width: 100%;        |
|min-w-min    |min-width: min-content;  |
|min-w-max    |min-width: max-content;  |
|max-w-none   |max-width: none;          |
|max-w-0      |max-width: 0rem;        |
|max-w-xs     |max-width: 20rem;       |
|max-w-sm     |max-width: 24rem;       |
|max-w-md     |max-width: 28rem;       |
|max-w-lg     |max-width: 32rem;       |
|max-w-xl     |max-width: 36rem;       |
|max-w-2xl    |max-width: 42rem;       |
|max-w-3xl    |max-width: 48rem;       |
|max-w-4xl    |max-width: 56rem;       |
|max-w-5xl    |max-width: 64rem;       |
|max-w-6xl    |max-width: 72rem;       |
|max-w-7xl    |max-width: 80rem;       |
|max-w-full   |max-width: 100%;        |
|max-w-min    |min-width: min-content;  |
|max-w-max    |min-width: max-content;  |
|max-w-prose   |width: 65ch;            |

## Service invocation returns a string type, which is hoped to be set into the context as a variable x

```javascript
{
  type: 'service',
  api: {
    url: "xxx"
    responseKey: "x"
  }
}
```

responseKey is identified by the Nop platform's ajax fetch function, whereas amis itself does not support this.

## Expression

* The expression can be used with the functions GETRENDERERDATA(id, path) and GETRENDERERPROP(id, path) to separately retrieve the data and properties of a specified component.
* After an http request is executed, the subsequent actions can be accessed via ${responseResult} or ${{outputVar}}, which returns the request response result.

## Setting combo data

1. The combo needs to have an id property assigned for the purpose of targeting specific events.
2. The popup button has configured a data mapping {comboIndex: "${index}"}, because CRUD operations' row data also contains the index variable, and when sending actions, the index variable should correspond to the current row's number. Therefore, when the popup is triggered, it assigns the combo's index value to comboIndex.
3. The crud operation bar has added a close button with a configuration: close: true. This means that after the action is completed, the popup will be closed automatically.
4. Inside the button, there is an onEvent configuration for click events. When clicked, it performs a setValue action and sets the index parameter to '${comboIndex}' using ${\&}. The symbol ${\&} represents a special syntax used to access the context's data in the application. Among these, ${\&} stands for "and," which is used to merge the values together, particularly in cases involving multiple parameters or expressions.

## Group Multiple Input Controls Together, Each Corresponding to a Variable
```json
{
  "type": "input-group",
  "name": "input-group",
  "label": "Input Group Validation",
  "body": [
    {
      "type": "input-text",
      "placeholder": "Please enter a number not exceeding 6 digits",
      "name": "group-input1",
      "label": "Sub Element One",
      "validations": {
        "isNumeric": true,
        "maxLength": 6
      }
    },
    {
      "type": "input-text",
      "placeholder": "Please enter a text with minimum length of 5 characters",
      "name": "group-input2",
      "required": true,
      "validations": {
        "minLength": 5
      }
    }
  ]
}
```

## API Return List Data

When retrieving list data, the `normalizeApiResponseData` function will automatically wrap the list in `{items: [list]}` format. This ensures that the returned `data` is always a `Map` object when accessed.

## Table Editing

* During row editing, the context environment may contain a variable `index`, corresponding to the current row's index.
* `TableRow` has an `itemIndex` property.
* The `IRow.change(values)` method can modify values.
* The `TableStore.getRowById(id)` method retrieves an `IRow`.
* `props.store.row` can retrieve the current row, and `store.rowIndex` corresponds to the row's index.

## Store Operations

* `store.changeValue('x', 123)`
*

## Debugging

You can insert a debugger statement in the `onClick` event.

```json
{
  "onClick": "debugger; props.store.closeDialog()"
}
```

## URL Parameters
In the design of AMIS, for URL parameters, there is a special assumption regarding parameters in URLs. For example, when a `picker` control has a `filter` segment and contains a `type` filter condition, it will override the `filter_type` variable in the URL and place it into the data segment.

```json
{
  "url": "/test",
  "data": {
    "filter_type": 1
  }
}
```

